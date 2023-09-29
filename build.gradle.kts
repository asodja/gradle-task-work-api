interface TaskWork<P : TaskWorkParameters>

interface TaskWorkParameters {
    interface None : TaskWorkParameters
}

interface TaskConfiguration<P : TaskWorkParameters> {
    val description: Property<String>
    val parameters: P
}

interface CompileWork : TaskWork<CompileWork.CompileWorkParameters> {
    interface CompileWorkParameters : TaskWorkParameters {
        @get:Input
        val input: RegularFileProperty
        // If not set, then output is auto assigned,
        // relative path by default
        @get:OutputFile
        val output: RegularFileProperty
    }
}

fun <P: TaskWorkParameters, T: TaskWork<P>> TaskContainer.register(v: String, b: Class<T>, action: Action<TaskConfiguration<P>>): P {
    TODO("Not yet implemented")
}

fun <P: TaskWorkParameters> TaskConfiguration<P>.parameters(action: Action<P>) {
    action.execute(parameters)
}

val firstParams = tasks.register("first", CompileWork::class.java) {
    description = "This is first compile task work"
    parameters {
        input = layout.projectDirectory.file("classes.txt")
    }
}

tasks.register("second", CompileWork::class.java) {
    description = "This is second compile task work"
    parameters {
        input = firstParams.output
    }
}