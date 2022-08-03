package me.tomasan7.tomfeedbackapi

class Placeholders(private val placeholders: Map<String, Any>)
{
    /**
     * Replaces all the placeholders in the string with their values.
     * Looks for placeholders inside {}.
     */
    fun apply(string: String): String
    {
        var resultString = string

        for (placeholder in placeholders.entries)
            resultString = resultString.replace("{${placeholder.key}}", placeholder.value.toString(), true)

        return resultString
    }

    override fun toString() = placeholders.toString()

    companion object
    {
        /**
         * Creates [Placeholders] instance, where a placeholder is first element and its value the following one.
         * Ignores any extra Strings
         */
        fun of(vararg placeholders: String): Placeholders
        {
            val resultMap = HashMap<String, String>()

            val size = placeholders.size.let { if (it % 2 == 0) it else it - 1 }

            for (i in 0 until size step 2)
                resultMap[placeholders[i]] = placeholders[i + 1]

            return Placeholders(resultMap)
        }
    }
}