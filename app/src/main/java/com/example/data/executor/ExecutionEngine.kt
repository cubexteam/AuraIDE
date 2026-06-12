package com.example.data.executor

import android.content.Context
import dalvik.system.DexClassLoader
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.io.PrintStream

class ExecutionEngine(private val context: Context) {
    
    fun execute(dexJarFile: File, mainClassName: String, ioBridge: IOBridge) {
        val optimizedDir = File(context.cacheDir, "dex_optimized").apply { mkdirs() }
        
        val classLoader = DexClassLoader(
            dexJarFile.absolutePath,
            optimizedDir.absolutePath,
            null,
            context.classLoader
        )
        
        val originalOut = System.out
        val originalErr = System.err
        val originalIn = System.`in`
        
        try {
            System.setOut(PrintStream(ioBridge.outputStream, true))
            System.setErr(PrintStream(ioBridge.errorStream, true))
            System.setIn(ioBridge.inputStream)
            
            val targetClass = classLoader.loadClass(mainClassName)
            val mainMethod = targetClass.getMethod("main", Array<String>::class.java)
            
            mainMethod.invoke(null, arrayOf<String>())
        } catch (e: Exception) {
            ioBridge.errorStream.write("${e.cause?.message ?: e.message}\n".toByteArray())
        } finally {
            System.setOut(originalOut)
            System.setErr(originalErr)
            System.setIn(originalIn)
        }
    }
}

interface IOBridge {
    val outputStream: OutputStream
    val errorStream: OutputStream
    val inputStream: InputStream
}
