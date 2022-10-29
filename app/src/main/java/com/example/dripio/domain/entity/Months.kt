package com.example.dripio.domain.entity

enum class Months(val label: String, val fullName: String, val index: Int) {
    JAN("JAN", "Janeiro", 1),
    FEB("FEV", "Fevereiro", 2),
    MAR("MAR", "Março", 3),
    APR("ABR", "Abril", 4),
    MAY("MAI", "Maio", 5),
    JUN("JUN", "Junho", 6),
    JUL("JUL", "Julho", 7),
    AUG("AGO", "Agosto", 8),
    SEP("SEP", "Setembro", 9),
    OCT("OUT", "Outubro", 10),
    NOV("NOV", "Novembro", 11),
    DEC("DEZ", "Dezembro", 12);

    companion object {
        fun findByIndex(index: Int): Months? = values().find { it.index == index }
    }
}