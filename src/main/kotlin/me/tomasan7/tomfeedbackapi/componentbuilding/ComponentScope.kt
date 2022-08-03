package me.tomasan7.tomfeedbackapi.componentbuilding

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration

class ComponentScope(
    private var component: Component = Component.empty()
)
{
    fun color(textColor: TextColor)
    {
        component = component.color(textColor)
    }

    fun decoration(decoration: TextDecoration, flag: Boolean)
    {
        component = component.decoration(decoration, flag)
    }

    fun text(text: String, block: ComponentScope.() -> Unit = {})
    {
        val componentScope = ComponentScope(Component.text(text))
        componentScope.block()
        component = component.append(componentScope.build())
    }

    fun keybind(keybind: String, block: ComponentScope.() -> Unit = {})
    {
        val componentScope = ComponentScope(Component.keybind(keybind))
        componentScope.block()
        component = component.append(componentScope.build())
    }

    fun component(component: Component)
    {
        this.component = this.component.append(component)
    }

    fun build() = component
}

fun buildComponent(block: ComponentScope.() -> Unit): Component
{
    val componentScope = ComponentScope()
    componentScope.block()
    return componentScope.build()
}