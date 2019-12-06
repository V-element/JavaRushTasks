package com.javarush.task.task31.task3111;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class SearchFileVisitor extends SimpleFileVisitor<Path> {

    private String partOfName;
    private String partOfContent;
    private int minSize = 0;
    private int maxSize = 0;
    private boolean partOfNameIsSet = false;
    private boolean partOfContentIsSet = false;
    private boolean minSizeIsSet = false;
    private boolean maxSizeIsSet = false;
    private List<Path> foundFiles = new ArrayList<>();

    public void setPartOfName(String partOfName) {
        this.partOfName = partOfName;
        this.partOfNameIsSet = true;
    }

    public void setPartOfContent(String partOfContent) {
        this.partOfContent = partOfContent;
        this.partOfContentIsSet = true;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
        this.minSizeIsSet = true;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
        this.maxSizeIsSet = true;
    }

    public List<Path> getFoundFiles() {
        return foundFiles;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        byte[] content = Files.readAllBytes(file); // размер файла: content.length

        if (!partOfNameIsSet && !partOfContentIsSet && !minSizeIsSet && !maxSizeIsSet) {
            foundFiles.add(file);
        } else {
            boolean fileAdd = true;
            if (partOfNameIsSet) {
                if (!file.getFileName().toString().contains(partOfName)) {
                    fileAdd = false;
                }
            }

            if (fileAdd && partOfContentIsSet) {
                if (!new String(content).contains(partOfContent)) {
                    fileAdd = false;
                }
            }

            if (fileAdd && minSizeIsSet) {
                if (content.length < minSize) {
                    fileAdd = false;
                }
            }

            if (fileAdd && maxSizeIsSet) {
                if (content.length > maxSize) {
                    fileAdd = false;
                }
            }

            if (fileAdd) {
                foundFiles.add(file);
            }
        }



        return super.visitFile(file, attrs);
    }
}
