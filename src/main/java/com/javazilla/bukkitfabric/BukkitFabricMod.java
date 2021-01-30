/**
 * CardboardPowered - Bukkit/Spigot for Fabric
 * Copyright (C) CardboardPowered.org and contributors
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either 
 * version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package com.javazilla.bukkitfabric;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.cardboardpowered.library.Library;
import org.cardboardpowered.library.LibraryKey;
import org.cardboardpowered.library.LibraryManager;

import com.google.common.collect.ImmutableMap;
import com.javazilla.bukkitfabric.nms.MappingsReader;
import com.javazilla.bukkitfabric.nms.ReflectionRemapper;

import net.fabricmc.api.ModInitializer;
import net.minecraft.server.network.ServerLoginNetworkHandler;

@SuppressWarnings("deprecation")
public class BukkitFabricMod implements ModInitializer {

    public static Logger LOGGER = BukkitLogger.getLogger(); 
    public static boolean isAfterWorldLoad = false;
    public static final Random random = new Random();

    public static List<ServerLoginNetworkHandler> NETWORK_CACHE = new ArrayList<>();
    public static Method GET_SERVER;

    @Override
    public void onInitialize() {
        LOGGER.info("");
        LOGGER.info("Cardboard Mod - CardboardPowered.org");
        LOGGER.info("(formerly known as Bukkit4Fabric)");
        LOGGER.info("");

        loadLibs();

        try {
            GET_SERVER = ReflectionRemapper.class.getMethod("getNmsServer");
        } catch (NoSuchMethodException | SecurityException e) {
        }

        try {
            MappingsReader.main(null);
            //if (FabricLoader.getInstance().isDevelopmentEnvironment())
            //    IngotReader.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("Cardboard mod Loaded.");
    }

    public static void loadLibs() {
        String repository = "https://repo.glowstone.net/repository/maven-public/";

        Map<LibraryKey, Library> libraries = Stream.of(
                new Library("org.xerial", "sqlite-jdbc", "3.21.0.1", LibraryManager.HashAlgorithm.SHA1, "81a0bcda2f100dc91dc402554f60ed2f696cded5", null),
                new Library("mysql", "mysql-connector-java", "5.1.46", LibraryManager.HashAlgorithm.SHA1, "9a3e63b387e376364211e96827bc27db8d7a92e9", null),
                new Library("commons-lang", "commons-lang", "2.6", LibraryManager.HashAlgorithm.SHA1, "0ce1edb914c94ebc388f086c6827e8bdeec71ac2", null)
            ).collect(ImmutableMap.toImmutableMap(Library::getLibraryKey, Function.identity()));
        new LibraryManager(repository, "lib", true, 2, libraries.values()).run();
        
        
        String repository2 = "https://repo1.maven.org/maven2/";

        Map<LibraryKey, Library> libraries2 = Stream.of(
                new Library("org.apache.commons", "commons-collections4", "4.4", LibraryManager.HashAlgorithm.SHA1, "62ebe7544cb7164d87e0637a2a6a2bdc981395e8", null),
                new Library("commons-collections", "commons-collections", "3.2.1", LibraryManager.HashAlgorithm.SHA1, "761ea405b9b37ced573d2df0d1e3a4e0f9edc668", null),
                new Library("com.google.code.gson", "gson", "2.8.6", LibraryManager.HashAlgorithm.SHA1, "9180733b7df8542621dc12e21e87557e8c99b8cb", null)
            ).collect(ImmutableMap.toImmutableMap(Library::getLibraryKey, Function.identity()));
        new LibraryManager(repository2, "lib", true, 2, libraries2.values()).run();

        String repository3 = "https://repo1.maven.org/maven2/";

        String internalVer = System.getProperty("java.class.version");
        double javaVer = Double.valueOf(internalVer) - 44;

        if (javaVer <= 8) {
            // The JDK and JRE are not separate post Java 8
            // So we don't need to worry about it.
            Map<LibraryKey, Library> libraries3 = Stream.of(
                    new Library("com.google.errorprone", "javac", "1.8.0-u20", LibraryManager.HashAlgorithm.SHA1, "b23b2b0e3f79e3f737496a9eca5bab65cdca791d", null)
                ).collect(ImmutableMap.toImmutableMap(Library::getLibraryKey, Function.identity()));
            new LibraryManager(repository3, "lib", true, 2, libraries3.values()).run();
        } else {
            File jdk = new File("lib", "javac-1.8.0-u20.jar");
            if (jdk.exists())
                jdk.delete();
        }
    }

}