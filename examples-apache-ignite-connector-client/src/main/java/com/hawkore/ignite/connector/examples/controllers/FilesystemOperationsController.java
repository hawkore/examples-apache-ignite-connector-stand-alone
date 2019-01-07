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
package com.hawkore.ignite.connector.examples.controllers;

import java.io.IOException;
import java.io.OutputStream;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.hawkore.ignite.connector.examples.services.operations.FilesystemOperationsService;

/**
 * TopicOperations REST Controller
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 *
 *
 */
@Path("/filesystem")
public class FilesystemOperationsController {

    @Autowired
    private FilesystemOperationsService filesystemOperationsService;

    /**
     * Creates a file with the given content.
     *
     * @param path
     *            File path to create.
     * @param aSimpleTextContent
     *            simple text content
     * @return a simple response
     */
    @POST
    @Path("/create")
    @Produces(MediaType.TEXT_PLAIN_VALUE)
    public Response filesystemCreate(@QueryParam("path") String path, @QueryParam("content") String aSimpleTextContent){
        filesystemOperationsService.filesystemCreate(path, aSimpleTextContent);
        return Response
            .status(200).entity("File successfully created").build();

    }

    /**
     * Deletes file.
     *
     * @param path
     *            File path to delete.
     * @return a simple response         
     */
    @DELETE
    @Path("/rm")
    @Produces(MediaType.TEXT_PLAIN_VALUE)
    public Response filesystemDelete(@QueryParam("path") String path) {
        filesystemOperationsService.filesystemDelete(path);
        return Response
            .status(200).entity("File successfully removed").build();
    }

    /**
     * Lists file paths under the specified path.
     *
     * @param path
     *            Path to list files under.
     * @return List of paths under the specified path.
     */
    @GET
    @Path("/ls")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response filesystemListFiles(@QueryParam("path") String path) {
        return Response
            .status(200).entity(filesystemOperationsService.filesystemListFiles(path)).build();
    }

    /**
     * Creates directories under specified path with the specified properties.
     * Note that the properties are applied only to created directories, but
     * never updated for existing ones.
     *
     * @param path
     *            Path of directories chain to create.
     * @return a simple response         
     */
    @POST
    @Path("/mkdirs")
    @Produces(MediaType.TEXT_PLAIN_VALUE)
    public Response filesystemMkdirs(@QueryParam("path") String path) {
        filesystemOperationsService.filesystemMkdirs(path);
        return Response
            .status(200).entity("Directories successfully created").build();
    }

    /**
     * Determines size of the file denoted by provided path. In case if path is
     * a directory, then total size of all containing entries will be calculated
     * recursively.
     * @param path
     *            File system path.
     * @return Total size..
     */
    @GET
    @Path("/size")
    @Produces(MediaType.TEXT_PLAIN_VALUE)
    public Response filesystemSize(@QueryParam("path") String path) {
        return Response
            .status(200).entity(filesystemOperationsService.filesystemSize(path)).build();
    }

    /**
     * Gets summary (total number of files, total number of directories and
     * total length) for a given path.
     *
     * @param path
     *            Path to get information for.
     * @return Summary object.
     */
    @GET
    @Path("/summary")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response filesystemSummary(@QueryParam("path") String path) {
        return Response
            .status(200).entity(filesystemOperationsService.filesystemSummary(path)).build();
    }

    /**
     * Download file
     *
     * @param path
     *            File path to read.
     * @return File input stream to read data from.
     */
    @GET
    @Path("/download")
    @Produces(MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Response filesystemOpen(@QueryParam("path") String path) {
        StreamingOutput stream = new StreamingOutput() {
            @Override
            public void write(OutputStream os) throws IOException {
                IOUtils.copy(filesystemOperationsService.filesystemOpen(path), os);
            }
        };
        return Response.ok(stream).build();
    }
}
