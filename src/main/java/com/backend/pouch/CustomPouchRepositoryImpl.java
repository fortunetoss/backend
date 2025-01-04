package com.backend.pouch;

import com.backend.oauth.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import static com.backend.oauth.QUser.*;
import static com.backend.pouch.QPouch.*;
import static com.backend.pouch.QQuestion.*;

@Repository
public class CustomPouchRepositoryImpl implements CustomPouchRepository {

    private EntityManager em;

    private JPAQueryFactory queryFactory;

    public CustomPouchRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }


    public Question findRandomQuestion(User findUser){
        Question findQuestion = queryFactory.select(question)
                .from(question)
                .where(question.id.notIn(
                        queryFactory.select(pouch.question.id)
                                .from(pouch)
                                .join(pouch.user, user)
                                .where(user.eq(findUser))
                ))
                .limit(1)
                .fetchOne();



        return findQuestion;

    }

}






