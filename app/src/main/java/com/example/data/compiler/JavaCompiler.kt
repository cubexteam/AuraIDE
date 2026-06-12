package com.example.data.compiler

import java.io.File
import java.io.PrintWriter
import java.io.StringWriter

class JavaCompiler {
    fun compile(sourceFile: File, outputDirectory: File, androidJarPath: String): CompilationResult {
        val outWriter = StringWriter()
        val errWriter = StringWriter()
        
        val args = arrayOf(
            "-1.8",
            "-source", "1.8",
            "-target", "1.8",
            "-cp", androidJarPath,
            "-d", outputDirectory.absolutePath,
            sourceFile.absolutePath
        )
        
        val success = org.eclipse.jdt.internal.compiler.batch.Main.compile(
            args, 
            PrintWriter(outWriter), 
            PrintWriter(errWriter), 
            null
        )
        
        return CompilationResult(
            isSuccess = success,
            outputLog = outWriter.toString(),
            errorLog = errWriter.toString()
        )
    }
}

data class CompilationResult(val isSuccess: Boolean, val outputLog: String, val errorLog: String)
