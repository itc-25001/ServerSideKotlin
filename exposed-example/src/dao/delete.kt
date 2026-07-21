package dao
import

fun main(){
    dbConnect()
    transaction {
        val entity = MemberEntity.findById(4)
        entity?.delete()
    }
}