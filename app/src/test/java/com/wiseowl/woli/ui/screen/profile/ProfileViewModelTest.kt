package com.wiseowl.woli.ui.screen.profile

import com.wiseowl.woli.ActionHandlerRule
import com.wiseowl.woli.domain.model.User
import com.wiseowl.woli.domain.repository.TestAccountRepository
import com.wiseowl.woli.domain.usecase.account.AccountUseCase
import com.wiseowl.woli.domain.usecase.profile.ProfileUseCase
import com.wiseowl.woli.ui.screen.profile.model.ProfileAction
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.runBlocking
import org.junit.Rule

class ProfileViewModelTest {

    private lateinit var profileUseCase: ProfileUseCase
    private lateinit var profileViewMode: ProfileViewModel
    private lateinit var accountUseCase: AccountUseCase
    private val fakeUser = User(
        "FirstName",
        "LastName",
        "12345",
        "fakeEmail@gmail.com",
        null
    )

    @get:Rule
    val actionHandlerRule = ActionHandlerRule()

    @Before
    fun setup() = runBlocking{
        accountUseCase = AccountUseCase(TestAccountRepository())
        profileUseCase = ProfileUseCase(accountUseCase)
        accountUseCase.createAccount(fakeUser.email, "Random", fakeUser.firstName, fakeUser.lastName)
        profileViewMode = ProfileViewModel(profileUseCase)
    }

    @Test
    fun deleteAccount_event_in_PageViewModel_deletes_account_from_repository() = runTest {
        val result1 = accountUseCase.doesAccountExists(fakeUser.email)
        assert(result1)
        profileViewMode.onEvent(ProfileAction.DeleteAccountRequest)
        accountUseCase.deleteAccount()
        val result2 = accountUseCase.doesAccountExists(fakeUser.firstName)
        assertFalse(result2)
    }
}