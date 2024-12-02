package com.example.ekacareassignment.presentation.userInfoScreen


import android.text.format.DateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ekacareassignment.core.utils.ADDRESS
import com.example.ekacareassignment.core.utils.AGE
import com.example.ekacareassignment.core.utils.CANCEL
import com.example.ekacareassignment.core.utils.DOB
import com.example.ekacareassignment.core.utils.DOB_FORMAT
import com.example.ekacareassignment.core.utils.NAME
import com.example.ekacareassignment.core.utils.OK
import com.example.ekacareassignment.core.utils.SELECT_DATE
import com.example.ekacareassignment.core.utils.SUBMIT
import com.example.ekacareassignment.core.utils.TOTAL_ENTRIES
import com.example.ekacareassignment.domain.model.User
import java.util.Calendar


@Composable
fun UserInfoScreenRoot(
    viewModel: UserInfoViewModel
) {
    val user by viewModel.user.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.toastMessage.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        UserInfoScreen(userEntity = user) { event ->
            viewModel.onEvent(event)
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(MaterialTheme.colorScheme.background)
        )
    }
}


@Composable
fun UserInfoScreen(
    userEntity: User, onEvent: (UserFormEvent) -> Unit
) {

    val selectedDate = remember { mutableStateOf<Long?>(null) }
    var isDatePickerVisible by remember { mutableStateOf(false) }
    val focusRequester = remember {
        FocusRequester()
    }
    val focusManager = LocalFocusManager.current

    val onDateSelected: (Long?) -> Unit = { selectedDateMillis ->
        selectedDateMillis?.let {
            val selectedDateString = DateFormat.format(DOB_FORMAT, it).toString()
            onEvent(UserFormEvent.EnteredDOB(selectedDateString))

            val calculatedAge = calculateAge(it)
            onEvent(UserFormEvent.EnteredAge(calculatedAge.toString()))
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(value = userEntity.name,
            onValueChange = { onEvent(UserFormEvent.EnteredName(it)) },
            label = { Text(NAME) },
            maxLines = 1,
            modifier = Modifier.focusRequester(focusRequester),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }))
        OutlinedTextField(value = userEntity.age.toString(),
            onValueChange = { newAge ->
                val validatedAge = newAge.toIntOrNull()?.coerceIn(0, 130)
                validatedAge?.let {
                    onEvent(UserFormEvent.EnteredAge(it.toString()))
                } ?: run {
                    onEvent(UserFormEvent.EnteredAge(""))
                }
            },
            maxLines = 1,
            label = { Text(AGE) },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next, keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            modifier = Modifier.focusRequester(focusRequester))
        OutlinedTextFieldWithDatePicker(
            selectedDate = selectedDate, onDateClick = {
                isDatePickerVisible = true
            }, focusManager = focusManager, focusRequester = focusRequester
        )

        OutlinedTextField(
            value = userEntity.address,
            onValueChange = { onEvent(UserFormEvent.EnteredAddress(it)) },
            label = { Text(ADDRESS) },
            modifier = Modifier.focusRequester(focusRequester),
            maxLines = 2
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(onClick = { onEvent(UserFormEvent.Submit) }) {
            Text(SUBMIT)
        }

        OutlinedButton(onClick = { onEvent(UserFormEvent.GetAllUsers) }) {
            Text(TOTAL_ENTRIES)
        }
    }
    if (isDatePickerVisible) {
        DatePickerModalInput(onDateSelected = { date ->
            selectedDate.value = date
            onDateSelected(date)
        }, onDismiss = { isDatePickerVisible = false })
    }


}

fun calculateAge(dobMillis: Long): Int {
    val dobCalendar = Calendar.getInstance().apply { timeInMillis = dobMillis }
    val today = Calendar.getInstance()
    var age = today.get(Calendar.YEAR) - dobCalendar.get(Calendar.YEAR)
    if (today.get(Calendar.DAY_OF_YEAR) < dobCalendar.get(Calendar.DAY_OF_YEAR)) {
        age--
    }
    return age
}

fun calculateDOB(age: Int): Long {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.YEAR, -age)
    return calendar.timeInMillis
}

@Composable
fun OutlinedTextFieldWithDatePicker(
    selectedDate: MutableState<Long?>,
    onDateClick: () -> Unit,
    focusManager: FocusManager,
    focusRequester: FocusRequester
) {
    val formattedDate = selectedDate.value?.let {
        DateFormat.format(DOB_FORMAT, it).toString()
    } ?: ""

    OutlinedTextField(value = formattedDate,
        onValueChange = {},
        label = { Text(DOB) },
        readOnly = true,
        maxLines = 1,
        modifier = Modifier.focusRequester(focusRequester),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next, keyboardType = KeyboardType.Number
        ),
        keyboardActions = KeyboardActions(onNext = {
            focusManager.moveFocus(FocusDirection.Down)
        }),
        trailingIcon = {
            IconButton(onClick = onDateClick) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = SELECT_DATE)
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModalInput(
    onDateSelected: (Long?) -> Unit, onDismiss: () -> Unit
) {
    val todayInMillis = Calendar.getInstance().timeInMillis

    val customSelectableDates = object : SelectableDates {
        override fun isSelectableDate(dateMillis: Long): Boolean {
            return dateMillis <= todayInMillis
        }
    }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = todayInMillis, selectableDates = customSelectableDates
    )


    DatePickerDialog(onDismissRequest = onDismiss, confirmButton = {
        TextButton(onClick = {
            val selectedDate = datePickerState.selectedDateMillis
            if (selectedDate != null) {
                onDateSelected(selectedDate)
            }
            onDismiss()
        }) {
            Text(OK)
        }
    }, dismissButton = {
        TextButton(onClick = onDismiss) {
            Text(CANCEL)
        }
    }) {
        DatePicker(state = datePickerState)
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewUserInputScreen() {
    UserInfoScreen(User(0, "Vansh", "23", "12/01/2001", "bangalore")) {}
}