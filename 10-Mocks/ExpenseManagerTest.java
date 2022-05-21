package put.io.testing.mocks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import put.io.students.fancylibrary.service.FancyService;

public class ExpenseManagerTest {


    @Test
    public void testCalculateTotal() {
        ExpenseRepository mockRepo = mock(ExpenseRepository.class);
        FancyService mockService = mock(FancyService.class);
        // config
        ArrayList<Expense> expenses = new ArrayList<>();

        expenses.add(new Expense(3));
        expenses.add(new Expense(2));
        expenses.add(new Expense(1));
        when(mockRepo.getExpenses()).thenReturn(expenses);

        // interaction
        ExpenseManager expenseManager = new ExpenseManager(mockRepo, mockService);

        assertEquals(expenseManager.calculateTotal(), 6);
    }

    @Test
    public void testCalculateTotalForCategory() {
        // config
        ExpenseRepository mockRepo = mock(ExpenseRepository.class);
        FancyService mockService = mock(FancyService.class);
        ArrayList<Expense> expensesHome = new ArrayList<>();
        ArrayList<Expense> expensesCar = new ArrayList<>();

        expensesHome.add(new Expense(3, "Home"));
        expensesHome.add(new Expense(2, "Home"));
        expensesCar.add(new Expense((long) 1, "Car"));
        expensesCar.add(new Expense((long) 9, "Car"));
        when(mockRepo.getExpensesByCategory(anyString())).thenReturn(Collections.emptyList());
        when(mockRepo.getExpensesByCategory("Home")).thenReturn(expensesHome);
        when(mockRepo.getExpensesByCategory("Car")).thenReturn(expensesCar);

        // interaction
        ExpenseManager expenseManager = new ExpenseManager(mockRepo, mockService);

        assertEquals(expenseManager.calculateTotalForCategory("Food"), 0);
        assertEquals(expenseManager.calculateTotalForCategory("Home"), 5);
        assertEquals(expenseManager.calculateTotalForCategory("Car"), 10);
    }


    @Test
    public void testCalculateTotalInDollars() throws ConnectException {
        // config
        ExpenseRepository mockRepo = mock(ExpenseRepository.class);

        FancyService fancyService = mock(FancyService.class);
        when(fancyService.convert(anyDouble(), eq("PLN"), eq("USD"))).thenAnswer(
                new Answer<Double>() {
                    @Override
                    public Double answer(InvocationOnMock i) throws Throwable {
                        double amount = (double) i.getArguments()[0];
                        return amount * 0.23;
                    }
                }
        );
        ArrayList<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense(3, "Home"));
        expenses.add(new Expense(2, "Home"));
        expenses.add(new Expense((long) 1, "Car"));
        expenses.add(new Expense((long) 9, "Car"));
        when(mockRepo.getExpenses()).thenReturn(expenses);

        // interaction
        ExpenseManager expenseManager = new ExpenseManager(mockRepo, fancyService);

        double exchRate = 0.23;

        assertEquals(expenseManager.calculateTotalInDollars(), (3+2+1+9) * exchRate);
        
    }
    @Test
    public void testCalculateTotalInDollarsHandleExc() throws ConnectException {
        // config
        ExpenseRepository mockRepo = mock(ExpenseRepository.class);

        FancyService fancyService = mock(FancyService.class);
        when(fancyService.convert(anyDouble(), eq("PLN"), eq("USD"))).thenThrow(new ConnectException());

        ArrayList<Expense> expenses = new ArrayList<>();
        when(mockRepo.getExpenses()).thenReturn(expenses);

        // interaction
        ExpenseManager expenseManager = new ExpenseManager(mockRepo, fancyService);

        assertEquals(expenseManager.calculateTotalInDollars(), -1);
    }
}
