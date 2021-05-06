package model

class Storybook {
    val groups: MutableList<StoryGroup> = mutableListOf()
    fun group(label: String, block: StoryGroup.() -> Unit) {
        val group = StoryGroup(label)
        group.block()
        groups.add(group)
    }
}

fun storybook(block: Storybook.() -> Unit): Storybook {
    val storybook = Storybook()
    storybook.block()
    return storybook
}