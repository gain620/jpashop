package com.inflearn.webproject.service;

import com.inflearn.webproject.entity.Member;
import com.inflearn.webproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository; // 컴파일 시점에 체크 가능 final

    // @RequiredArgsConstructor -> final 필드만 가지고 생성자 만들어줌
//    @Autowired // 최신 버전에서는 autowired 없어서도 파라미터 1개 생성자에 한해 의존성 주입해줌
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
    * 회원가입
    */
    @Transactional // write readOnly  = true 있으면 적용X
    public Long join(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        // 아래 로직은 동시성 문제 존재
        // 정합성을 위해 DB단에서 member name -> unique 제약 필요!
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
