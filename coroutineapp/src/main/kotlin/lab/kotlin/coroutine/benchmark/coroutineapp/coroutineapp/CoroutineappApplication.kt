package lab.kotlin.coroutine.benchmark.coroutineapp.coroutineapp

import com.sun.management.OperatingSystemMXBean
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import java.lang.management.ManagementFactory
import java.lang.management.MemoryUsage


@SpringBootApplication
@EnableReactiveMongoRepositories
class CoroutineappApplication

fun main(args: Array<String>) {
    runApplication<CoroutineappApplication>(*args)
    showServerInfo()
}

fun showServerInfo() {
    val memoryBean = ManagementFactory.getMemoryMXBean()
    val heapMemoryUsage: MemoryUsage = memoryBean.heapMemoryUsage

    val maxHeapSize = heapMemoryUsage.max
    val usedHeapSize = heapMemoryUsage.used

    println("Max Heap Size: ${formatSize(maxHeapSize)}")
    println("Used Heap Size: ${formatSize(usedHeapSize)}")

    val osBean = ManagementFactory.getOperatingSystemMXBean()
    val totalCore = osBean.availableProcessors
    val osBeancpu = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean::class.java)

    println("process cpu load: " + (osBeancpu.processCpuLoad * 100).toString() + "%")
    println("system cpu load: " + (osBeancpu.cpuLoad * 100).toString() + "%")

    val bean = ManagementFactory.getPlatformMXBean(com.sun.management.OperatingSystemMXBean::class.java)
    val max = bean.totalMemorySize

    println("Total Server Core: $totalCore")
    println("Total jvm size: ${formatSize(max)}")
}

fun formatSize(size: Long): String {
    val unit = 1024
    if (size < unit) return "$size B"
    val exp = (Math.log(size.toDouble()) / Math.log(unit.toDouble())).toInt()
    val pre = "KMGTPE"[exp - 1]
    return String.format("%.1f %sB", size / Math.pow(unit.toDouble(), exp.toDouble()), pre)
}
