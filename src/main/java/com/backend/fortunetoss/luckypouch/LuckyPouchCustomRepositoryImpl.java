package com.backend.fortunetoss.luckypouch;

import com.backend.fortunetoss.shape.QShape;
import com.backend.fortunetoss.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.backend.fortunetoss.luckypouch.QLuckyPouch.luckyPouch;
import static com.backend.fortunetoss.shape.QShape.*;
import static com.backend.fortunetoss.user.QUser.user;

@Repository
public class LuckyPouchCustomRepositoryImpl implements LuckyPouchCustomRepository{

    private EntityManager em;

    private JPAQueryFactory queryFactory;

    public LuckyPouchCustomRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }


    public List<LuckyPouch> findUsersLuckyPouches(User finduser) {

        List<LuckyPouch> luckyPouches = queryFactory.select(luckyPouch)
                .from(luckyPouch)
                .join(luckyPouch.shape, shape).fetchJoin()
                .where(luckyPouch.user.eq(finduser))
                .fetch();

        return luckyPouches;

    }
}
