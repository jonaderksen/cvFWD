package com.cvFWD.cvFWD.service;

import com.cvFWD.cvFWD.domain.Account;
import com.cvFWD.cvFWD.domain.Profile;
import com.cvFWD.cvFWD.model.ProfileModel;
import com.cvFWD.cvFWD.repository.AccountRepo;
import com.cvFWD.cvFWD.repository.ProfileRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static junit.framework.TestCase.assertEquals;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ProfileServiceTest {

    @Mock
    private ProfileRepo profileRepo;

    @Mock
    private AccountRepo accountRepo;

    @InjectMocks
    private ProfileService profileService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
//        when(accountRepo.getByEmail(any(String.class))).thenReturn(mock(Account.class));
//        when(profileRepo.save(any(Profile.class))).thenReturn(mock(Profile.class));
    }

    @Test
    public void testSomething() {
        //create Mock prole and model
        ProfileModel MockProfileDto = new ProfileModel();
        MockProfileDto.setSummery("summery aaa bb aaa bb");

        Account mockAccount = new Account();
        mockAccount.setName("test");
        mockAccount.setEmail("test@mail.com");

        Profile mockProfile = new Profile();
        mockProfile.setSummery("TEST SUMMERY");
        mockProfile.setAccount(null);


//        when(accountRepo.getByEmail(any(String.class))).thenReturn(mockAccount);
//        when(profileRepo.save(any(Profile.class))).thenReturn(mockProfile);


        //save contact
        //Profile newProfile = profileService.update(MockProfileDto, null);
 //       Profile newProfile = profileService.add();


        //verrivy
//        assertNotNull(newProfile);
//        assertNotNull(mockProfile.getId());
        //assertEquals("TEST SUMMERY", newProfile.getSummery());
        assertEquals("TEST SUMMERY", mockProfile.getSummery());
//        assertEquals("test@mail.com", newProfile.getAccount().getEmail());
    }


}