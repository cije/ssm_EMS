package com.ce.dao;

import com.ce.domain.Member;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

public interface MemberDao {
    @Select("select * from member where id=#{id} limit 1")
    Member findById(String id);
}
