package gift.service;

import gift.dto.AuthResponse;
import gift.dto.LoginRequest;
import gift.dto.RegisterRequest;
import gift.exception.DuplicatedEmailException;
import gift.exception.InvalidLoginInfoException;
import gift.exception.UnauthorizedAccessException;
import gift.model.Member;
import gift.model.MemberRole;
import gift.repository.MemberRepository;
import gift.service.auth.AuthService;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthService authService;

    public MemberService(MemberRepository memberRepository, AuthService authService) {
        this.memberRepository = memberRepository;
        this.authService = authService;
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        emailValidation(registerRequest.email());
        var member = createMemberWithMemberRequest(registerRequest);
        var savedMember = memberRepository.save(member);
        var token = authService.createAccessTokenWithMember(savedMember);
        return AuthResponse.from(token);
    }

    public AuthResponse login(LoginRequest loginRequest) {
        var member = memberRepository.findByEmail(loginRequest.email());
        loginInfoValidation(member, loginRequest.password());
        var token = authService.createAccessTokenWithMember(member);
        return AuthResponse.from(token);
    }

    public void deleteMember(Long id, String header) {
        var token = authService.getTokenWithAuthorizationHeader(header);
        deleteValidation(id, token);
        memberRepository.deleteById(id);
    }

    private void emailValidation(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new DuplicatedEmailException("이미 존재하는 이메일입니다.");
        }
    }

    private void loginInfoValidation(Member member, String password) {
        if (!member.getPassword().equals(password)) {
            throw new InvalidLoginInfoException("로그인 정보가 유효하지 않습니다.");
        }
    }

    private void deleteValidation(Long id, String token) {
        if (isAdmin(token)) return;
        if (isMemberId(id, token)) return;
        throw new UnauthorizedAccessException("인가되지 않은 요청입니다.");
    }

    private boolean isAdmin(String token) {
        var memberRoleWithToken = authService.getMemberRoleWithToken(token);
        if (memberRoleWithToken.equals(MemberRole.ADMIN)) return true;
        return false;
    }

    private boolean isMemberId(Long id, String token) {
        var memberIdWithToken = authService.getMemberIdWithToken(token);
        if (id.equals(memberIdWithToken)) return true;
        return false;
    }

    private Member createMemberWithMemberRequest(RegisterRequest registerRequest) {
        return new Member(registerRequest.name(), registerRequest.email(), registerRequest.password(), MemberRole.valueOf(registerRequest.role()));
    }
}
