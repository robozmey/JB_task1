import kotlin.system.exitProcess

typealias CharGraph = Map<Char, Set<Char>>

fun getGraph(artemSurname: String, namesList: List<String>): CharGraph {
    val charGraph = artemSurname.map { it to mutableSetOf<Char>() }.toMap()
    var namesListTmp = namesList
    val countOfBigger = artemSurname.map { it to 0 }.toMap().toMutableMap()
    artemSurname.withIndex().forEach { (index, artemChar) ->
        namesListTmp.forEach { name ->
            if (index < name.length && artemSurname.contains(name[index]) && artemChar != name[index]) {
                charGraph[artemChar]!!.add(name[index])
                countOfBigger[name[index]] = countOfBigger[name[index]]!! + 1
            }
        }
        namesListTmp = namesListTmp.filter { index < it.length && it[index] == artemChar }
    }
    return charGraph
}

fun getCountsOfBigger(artemSurname: String, charGraph: CharGraph): MutableMap<Char, Int> {
    val countsOfBigger = artemSurname.map { it to 0 }.toMap().toMutableMap()
    charGraph.forEach { (_, setTo) ->
        setTo.forEach {
            countsOfBigger[it] = countsOfBigger[it]!! + 1
        }
    }
    return countsOfBigger
}

fun getCharOrder(artemSurname: String, charGraph: CharGraph): String {
    val countOfBigger = getCountsOfBigger(artemSurname, charGraph)

    var charOrder = ""
    val biggests = artemSurname.map { it }.filter { countOfBigger[it] == 0 }.toMutableList()
    while (biggests.isNotEmpty()) {
        val biggest = biggests.first()
        biggests.removeFirst()
        charOrder += biggest
        charGraph[biggest]!!.forEach {
            countOfBigger[it] = countOfBigger[it]!! - 1
            if (countOfBigger[it] == 0)
                biggests.add(it)
        }
    }
    return charOrder
}

fun main() {
    val namesCount = readLine()!!.toInt()
    val artemSurname = readLine()!!.toLowerCase()
    val namesList = List(namesCount - 1) { readLine()!!.toLowerCase() }

    val charGraph = getGraph(artemSurname, namesList)

    var charOrder = getCharOrder(artemSurname, charGraph)

    if (charOrder.length != charGraph.size) {
        println("Impossible")
        exitProcess(0)
    }

    for (char in 'a' until 'z') {
        if (!charOrder.contains(char))
            charOrder += char
    }

    println(charOrder)

}

