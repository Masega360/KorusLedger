package com.korus.core.infrastructure

import com.korus.core.application.UserRepository
import com.korus.core.domain.User
import com.korus.core.infrastructure.WalletTable.name
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.UUID

@Repository

class PostgresUserRepository: UserRepository {
    override fun save(user: User): User {
        transaction {
            UserTable.insert {row ->
                row[userId] = user.userId
                row[name] = user.name
                row[email] = user.email
                row[phoneNumber] = user.phoneNumber
                row[createdAt] = user.createdAt
                row[passwordHash] = user.passwordHash

            }
        }
        return user
    }

    override fun findAll(
        userId: UUID?,
        name: String?,
        email: String?,
        createdAt: LocalDateTime?
    ): List<User> {
        return transaction {
            val query = UserTable.selectAll()

            userId?.let { query.andWhere { UserTable.userId eq it } }
            name?.let { query.andWhere { UserTable.name eq it } }
            email?.let { query.andWhere { UserTable.email eq it } }
            createdAt?.let { query.andWhere { UserTable.createdAt eq it } }

            query.orderBy(UserTable.createdAt to SortOrder.DESC)
                .map { row ->
                    User(
                        userId = row[UserTable.userId],
                        name = row[UserTable.name],
                        email = row[UserTable.email],
                        passwordHash = row[UserTable.passwordHash],
                        createdAt = row[UserTable.createdAt],
                        phoneNumber = row[UserTable.phoneNumber],
                    )
                }
        }
    }

    override fun findByEmail(email: String): User? {
        return transaction {
            UserTable.select { UserTable.email eq email }
                .map { row ->
                    User(
                        userId = row[UserTable.userId],
                        name = row[UserTable.name],
                        email = row[UserTable.email],
                        passwordHash = row[UserTable.passwordHash],
                        createdAt = row[UserTable.createdAt],
                        phoneNumber = row[UserTable.phoneNumber]
                    )
                }
                .singleOrNull()
        }
    }

}

