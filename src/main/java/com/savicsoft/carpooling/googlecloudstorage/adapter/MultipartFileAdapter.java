package com.savicsoft.carpooling.googlecloudstorage.adapter;

import com.google.cloud.storage.Blob;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MultipartFileAdapter implements MultipartFile {

    private final Blob blob;

    public MultipartFileAdapter(Blob blob) {
        this.blob = blob;
    }

    @Override
    public String getName() {
        return blob.getName();
    }

    @Override
    public String getOriginalFilename() {
        return blob.getName();
    }

    @Override
    public String getContentType() {
        return blob.getContentType();
    }

    @Override
    public boolean isEmpty() {
        return blob.getSize() == 0;
    }

    @Override
    public long getSize() {
        return blob.getSize();
    }

    @Override
    public byte[] getBytes() throws IOException {
        return blob.getContent();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return (InputStream) blob.reader();
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        blob.downloadTo(dest.toPath());
    }

}
