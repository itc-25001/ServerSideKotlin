data class Number(val value: Int){
    fun isOdd(): Boolean{
        return value % 2 != 0
    }
    fun isRange(min: Int,max: Int): Boolean{
        return value in min..max
    }
}
