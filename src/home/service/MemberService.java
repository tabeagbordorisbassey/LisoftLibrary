package home.service;

import home.model.Member;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class MemberService {
    private final Map<String, Member> membersById = new LinkedHashMap<String, Member>();

    public void register(Member member) {
        if (member == null) throw new IllegalArgumentException("Member is required.");
        if (membersById.containsKey(member.getId())) {
            throw new IllegalArgumentException("Member already registered: " + member.getId());
        }
        membersById.put(member.getId(), member);
    }

    public Optional<Member> findMember(String id) {
        if (id == null || id.trim().isEmpty()) return Optional.empty();
        return Optional.ofNullable(membersById.get(id));
    }
}
