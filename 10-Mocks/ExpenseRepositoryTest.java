package put.io.testing.mocks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.InOrder;

import org.mockito.Mockito;
import put.io.students.fancylibrary.database.FancyDatabase;
import put.io.students.fancylibrary.database.IFancyDatabase;



public class ExpenseRepositoryTest {
    ExpenseRepository expRepo;


    @Test
    void testLoadExpenses() {
        IFancyDatabase mockDB = mock(IFancyDatabase.class);
        // config
        when(mockDB.queryAll()).thenReturn(Collections.emptyList());

        expRepo = new ExpenseRepository(mockDB);

        expRepo.loadExpenses();

        InOrder inOrder = inOrder(mockDB);
        inOrder.verify(mockDB).connect();
        inOrder.verify(mockDB).queryAll();
        inOrder.verify(mockDB).close();

        assertEquals(0, expRepo.getExpenses().size());

    }

    @Test
     void testSaveExpenses() {
        // creating
        IFancyDatabase mockDB = mock(IFancyDatabase.class);
        // config
        when(mockDB.queryAll()).thenReturn(Collections.emptyList());


        // interaction
        expRepo = new ExpenseRepository(mockDB);

        expRepo.loadExpenses();
        // Expense expense1 = new Expense();
        for (int i = 0; i < 5; i++) {
            expRepo.addExpense(new Expense());
        }

        expRepo.saveExpenses();

        // verification
        InOrder inOrder = inOrder(mockDB);
        inOrder.verify(mockDB).connect();
        inOrder.verify(mockDB).queryAll();
        inOrder.verify(mockDB).close();

        verify(mockDB, times(5)).persist(any(Expense.class));



    }
}
