package com.a3wcm.account.dto.mapper;

import com.a3wcm.account.domain.Account;
import com.a3wcm.account.domain.User;
import com.a3wcm.account.dto.AccountDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    Account accountDtoToAccount(AccountDto accountDto);

    @Mapping(source = "email", target = "username")
    User accountDtoToUser(AccountDto accountDto);

}
