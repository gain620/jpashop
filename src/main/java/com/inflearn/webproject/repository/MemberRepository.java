package com.inflearn.webproject.repository;

import com.inflearn.webproject.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

//    @PersistenceContext
//    EntityManager em;
    // Spring Data JPA 에서 지원해줘서 가능 @PersistenceContext => @Autowired
    private final EntityManager em;

    public Long save(Member member) {
        em.persist(member); // 영속성 컨텐스트에 의해 pk 값 보장
        return member.getId();
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select  m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String name) {
        return  em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
