package com.jerotes.jerotesvillage.compat.tacz;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.*;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;

import javax.annotation.Nullable;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import net.minecraftforge.fml.ModList;

public class MerorsResourceManager {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final Instant FIXED_TIME = Instant.parse("2024-01-01T00:00:00.000Z");
    private static final Path BACKUP_PATH = FMLPaths.GAMEDIR.get().resolve("tacz_backup");
    private static final SimpleDateFormat BACKUP_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd-HHmmss");
    private static final int MAX_BACKUP_COUNT = 5;

    private static final String MOD_RESOURCE_FOLDER = "MerorsEnergeticGun1.20.1";

    private MerorsResourceManager() {
    }

    public static void initializeResources() {
        try {
            Path targetDir = FMLPaths.GAMEDIR.get().resolve("tacz").resolve(MOD_RESOURCE_FOLDER);
            Path sourcePath = Paths.get(MOD_RESOURCE_FOLDER);

            LOGGER.info("Initializing MerorsEnergeticGun resources...");
            LOGGER.info("Source: {}", sourcePath);
            LOGGER.info("Target: {}", targetDir);

            copyModDirectory(sourcePath.toString(), targetDir.getParent(), MOD_RESOURCE_FOLDER);

            reloadTaczResources();

            LOGGER.info("Resources initialized successfully");

        } catch (Exception e) {
            LOGGER.error("Failed to initialize resources", e);
        }
    }
    private static void reloadTaczResources() {
        try {
            LOGGER.info("Reloading Tacz resources...");

            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> MerorsResourceManager::reloadClient);
            DistExecutor.safeRunWhenOn(Dist.DEDICATED_SERVER, () -> MerorsResourceManager::reloadServer);

            LOGGER.info("Tacz resources reloaded successfully");
        } catch (Exception e) {
            LOGGER.error("Failed to reload Tacz resources", e);
        }
    }


    private static void reloadClient() {
        if (!ModList.get().isLoaded("tacz")) {
            LOGGER.info("tacz mod not loaded, skipping ClientAssetsManager reload");
            fallbackReload();
            return;
        }

        try {
            Class<?> clientAssetsManagerClass = Class.forName("com.tacz.client.ClientAssetsManager");
            Method reloadAllPackMethod = clientAssetsManagerClass.getMethod("reloadAllPack");
            reloadAllPackMethod.invoke(null);
            LOGGER.info("ClientAssetsManager.reloadAllPack() called successfully via reflection");
        } catch (ClassNotFoundException e) {
            LOGGER.error("tacz mod classes not found", e);
            fallbackReload();
        } catch (NoSuchMethodException e) {
            LOGGER.error("ClientAssetsManager.reloadAllPack method not found", e);
            fallbackReload();
        } catch (InvocationTargetException | IllegalAccessException e) {
            LOGGER.error("Failed to invoke ClientAssetsManager.reloadAllPack()", e);
            fallbackReload();
        }
    }

    private static void reloadServer() {
        if (!ModList.get().isLoaded("tacz")) {
            LOGGER.info("tacz mod not loaded, skipping CommonAssetsManager reload");
            return;
        }

        try {
            Class<?> commonAssetsManagerClass = Class.forName("com.tacz.common.CommonAssetsManager");
            Method reloadAllPackMethod = commonAssetsManagerClass.getMethod("reloadAllPack");
            reloadAllPackMethod.invoke(null);
            LOGGER.info("CommonAssetsManager.reloadAllPack() called successfully via reflection");
        } catch (ClassNotFoundException e) {
            LOGGER.error("tacz mod classes not found", e);
        } catch (NoSuchMethodException e) {
            LOGGER.error("CommonAssetsManager.reloadAllPack method not found", e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            LOGGER.error("Failed to invoke CommonAssetsManager.reloadAllPack()", e);
        }
    }

    private static void fallbackReload() {
        LOGGER.warn("Using fallback reload method");
        try {
            Class<?> reloadClass = Class.forName("com.tacz.guns.ReloadCommand");
            Method reloadMethod = reloadClass.getMethod("reloadClient");
            reloadMethod.invoke(null);
            LOGGER.info("Fallback reload via reflection successful");
        } catch (Exception e) {
            LOGGER.error("Fallback reload via reflection failed", e);
        }
    }
    public static void copyModDirectory(String srcPath, Path root, String path) {
        String correctedPath = "/MerorsEnergeticGun1.20.1";
        LOGGER.info("Using corrected path: {}", correctedPath);

        URL url = MerorsResourceManager.class.getResource(correctedPath);
        if (url == null) {
            LOGGER.error("Resource not found with corrected path: {}", correctedPath);
            return;
        }

        try {
            copyFolder(url.toURI(), root.resolve(path));
        } catch (IOException | URISyntaxException e) {
            LOGGER.error("Failed to copy directory", e);
        }
    }
    public static void copyModFile(String srcPath, Path root, String path) {
        URL url = MerorsResourceManager.class.getResource(srcPath);
        if (url == null) {
            LOGGER.warn("File not found in JAR: {}", srcPath);
            return;
        }

        try {
            File targetFile = root.resolve(path).toFile();
            FileUtils.copyURLToFile(url, targetFile);
            LOGGER.debug("Copied file: {} -> {}", srcPath, targetFile);
        } catch (IOException e) {
            LOGGER.error("Failed to copy file: {}", srcPath, e);
        }
    }

    @Nullable
    public static InputStream readModFile(String filePath) {
        URL url = MerorsResourceManager.class.getResource(filePath);
        if (url == null) {
            return null;
        }

        try {
            return url.openStream();
        } catch (IOException e) {
            LOGGER.error("Failed to read file: {}", filePath, e);
            return null;
        }
    }
    public static boolean needsUpdate() {
        Path targetDir = FMLPaths.GAMEDIR.get().resolve("tacz").resolve(MOD_RESOURCE_FOLDER);

        if (!Files.exists(targetDir)) {
            return true;
        }

        Path versionFile = targetDir.resolve(".version");
        if (!Files.exists(versionFile)) {
            return true;
        }

        try {
            String currentVersion = getCurrentModVersion();
            String installedVersion = Files.readString(versionFile).trim();

            return !currentVersion.equals(installedVersion);

        } catch (IOException e) {
            LOGGER.debug("Failed to read version file", e);
            return true;
        }
    }

    private static void copyFolder(URI sourceURI, Path targetPath) throws IOException {
        LOGGER.info("Copying folder from {} to {}", sourceURI, targetPath);

        if (Files.isDirectory(targetPath)) {
            LOGGER.info("Target directory exists, creating backup...");
            backupDirectory(targetPath);

            deleteDirectory(targetPath);
            LOGGER.info("Old directory removed");
        }

        Files.createDirectories(targetPath);

        try (Stream<Path> stream = Files.walk(Paths.get(sourceURI), Integer.MAX_VALUE)) {
            stream.forEach(source -> {
                try {
                    String relativePath = sourceURI.relativize(source.toUri()).toString();
                    Path target = targetPath.resolve(relativePath);

                    if (Files.isDirectory(source)) {
                        Files.createDirectories(target);
                    } else {
                        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                        LOGGER.debug("Copied: {} -> {}", relativePath, target);
                    }
                } catch (IOException e) {
                    LOGGER.error("Error copying file", e);
                }
            });
        }

        writeVersionFile(targetPath);
    }

    private static void backupDirectory(Path targetPath) throws IOException {
        String dirName = targetPath.getFileName().toString();
        Path backupDir = BACKUP_PATH.resolve(dirName);

        if (!Files.exists(backupDir)) {
            Files.createDirectories(backupDir);
        }

        cleanupOldBackups(backupDir);

        String timestamp = BACKUP_DATE_FORMAT.format(new Date());
        Path backupFile = backupDir.resolve(String.format("%s-%s.zip", dirName, timestamp));

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(backupFile.toFile()))) {
            FileTime fixedTime = FileTime.from(FIXED_TIME);

            Files.walk(targetPath)
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        try {
                            String relativePath = targetPath.relativize(file).toString();
                            ZipEntry entry = new ZipEntry(relativePath);
                            entry.setLastModifiedTime(fixedTime);

                            zos.putNextEntry(entry);
                            Files.copy(file, zos);
                            zos.closeEntry();

                            LOGGER.debug("Backed up: {}", relativePath);
                        } catch (IOException e) {
                            LOGGER.warn("Failed to backup file: {}", file, e);
                        }
                    });
        }

        LOGGER.info("Backup created: {}", backupFile);
    }

    private static void cleanupOldBackups(Path backupDir) {
        try {
            File[] backupFiles = backupDir.toFile().listFiles((dir, name) -> name.endsWith(".zip"));
            if (backupFiles == null || backupFiles.length <= MAX_BACKUP_COUNT) {
                return;
            }

            Arrays.sort(backupFiles, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);

            for (int i = 0; i < backupFiles.length - MAX_BACKUP_COUNT; i++) {
                File oldBackup = backupFiles[i];
                if (FileUtils.deleteQuietly(oldBackup)) {
                    LOGGER.info("Deleted old backup: {}", oldBackup.getName());
                }
            }

        } catch (Exception e) {
            LOGGER.warn("Failed to cleanup old backups", e);
        }
    }

    private static void deleteDirectory(Path path) throws IOException {
        if (!Files.exists(path)) {
            return;
        }

        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private static void writeVersionFile(Path targetDir) throws IOException {
        String version = getCurrentModVersion();
        Path versionFile = targetDir.resolve(".version");
        Files.writeString(versionFile, version);
    }

    private static String getCurrentModVersion() {
        return "1.0.0";
    }
    public static List<String> listResources() {
        List<String> resources = new ArrayList<>();
        String resourcePath = "/" + MOD_RESOURCE_FOLDER;

        try {
            URL url = MerorsResourceManager.class.getResource(resourcePath);
            if (url == null) {
                return resources;
            }

            try (Stream<Path> stream = Files.walk(Paths.get(url.toURI()), Integer.MAX_VALUE)) {
                stream.map(path -> resourcePath + "/" + path.getFileName().toString())
                        .forEach(resources::add);
            }

        } catch (Exception e) {
            LOGGER.error("Failed to list resources", e);
        }

        return resources;
    }
}