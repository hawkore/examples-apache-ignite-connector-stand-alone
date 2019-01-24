/*
 * Copyright (C) 2018 HAWKORE S.L. (http://hawkore.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hawkore.ignite.connector.examples.services.operations;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.hawkore.ignite.connector.examples.AService;
import com.hawkore.ignite.extensions.api.wrappers.IgfsFileWrapper;
import com.hawkore.ignite.extensions.api.wrappers.IgfsPathSummaryWrapper;
import com.hawkore.ignite.extensions.internal.operations.FileSystemIgniteOperationsSvc;

/**
 * 
 * FilesystemOperationsService 
 *
 * @TODO: Implement some samples for missing operations
 * 
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 *
 *
 */
public class FilesystemOperationsService extends AService {

    /**
     * Creates a file with the given content.
     *
     * @param path
     *            File path to create.
     * @param aSimpleTextContent
     *            simple text content
     */
    public void filesystemCreate(String path, String aSimpleTextContent) {

        final String fileSystem = AService.FILE_SYSTEM_NAME;
        final InputStream content = new ByteArrayInputStream(aSimpleTextContent.getBytes());
        final int bufferSize = 0; // use default value
        final int blockSize = 0; // use default value
        final boolean overwrite = true;
        final String affinityKey = null;
        final short replication = 0; // use default value
        final Map<String, String> properties = null;

        FileSystemIgniteOperationsSvc.filesystemCreate(fileSystem, path, content, bufferSize, blockSize, overwrite,
            affinityKey, replication, properties, connection);
    }

    /**
     * Deletes file.
     *
     * @param path
     *            File path to delete.
     */
    public void filesystemDelete(String path) {

        final String fileSystem = AService.FILE_SYSTEM_NAME;
        boolean recursive = true;

        FileSystemIgniteOperationsSvc.filesystemDelete(fileSystem, path, recursive, connection);
    }

    /**
     * Lists file paths under the specified path.
     *
     * @param path
     *            Path to list files under.
     * @return List of paths under the specified path.
     */
    public List<IgfsFileWrapper> filesystemListFiles(String path) {

        final String fileSystem = AService.FILE_SYSTEM_NAME;

        return FileSystemIgniteOperationsSvc.filesystemListFiles(fileSystem, path, connection);
    }

    /**
     * Creates directories under specified path with the specified properties.
     * Note that the properties are applied only to created directories, but
     * never updated for existing ones.
     *
     * @param path
     *            Path of directories chain to create.
     */
    public void filesystemMkdirs(String path) {
        final String fileSystem = AService.FILE_SYSTEM_NAME;
        final Map<String, String> properties = null;
        FileSystemIgniteOperationsSvc.filesystemMkdirs(fileSystem, path, properties, connection);
    }

    /**
     * Gets summary (total number of files, total number of directories and
     * total length) for a given path.
     *
     * @param path
     *            Path to get information for.
     * @return Summary object.
     */
    public IgfsPathSummaryWrapper filesystemSummary(String path) {
        final String fileSystem = AService.FILE_SYSTEM_NAME;
        return FileSystemIgniteOperationsSvc.filesystemSummary(fileSystem, path, connection);
    }

    /**
     * Opens a file for reading.
     *
     * @param path
     *            File path to read.
     * @return File input stream to read data from.
     */
    public InputStream filesystemOpen(String path) {
        final String fileSystem = AService.FILE_SYSTEM_NAME;
        final int bufferSize = 0; // use default
        final int seqReadsBeforePrefetch = 0; // use default
        return FileSystemIgniteOperationsSvc.filesystemOpen(fileSystem, path, bufferSize, seqReadsBeforePrefetch,
            connection);
    }
    
    /**
     * Determines size of the file denoted by provided path. In case if path is
     * a directory, then total size of all containing entries will be calculated
     * recursively.
     * @param path
     *            File system path.
     * @return Total size..
     */
    public long filesystemSize(String path) {
        final String fileSystem = AService.FILE_SYSTEM_NAME;
        return FileSystemIgniteOperationsSvc.filesystemSize(fileSystem, path, connection);
    }

    // other operations
    // filesystemExists
    // filesystemFormat
    // filesystemInfo
    // filesystemListPaths
    // filesystemMkdirs
    // filesystemOpen
    // filesystemRename
    // filesystemSetTimes
    // filesystemSize
    // filesystemUpdate
    // filesystemUsedSpaceSize
    // filesystemAppend
    // filesystemCloseStream

}
