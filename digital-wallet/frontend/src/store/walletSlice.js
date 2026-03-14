import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

// Base URL for API
const API_URL = 'http://localhost:8080/api';

// Async Thunks
export const fetchBalance = createAsyncThunk(
  'wallet/fetchBalance',
  async (customerId, { rejectWithValue }) => {
    try {
      // In a real app with Spring Security, customerId might be taken from context
      // Assuming a GET endpoint exists or using transactions to derive balance
      const response = await axios.get(`${API_URL}/balance?customerId=${customerId}`);
      return response.data;
    } catch (error) {
      return rejectWithValue(error.response?.data?.message || 'Failed to fetch balance');
    }
  }
);

export const sendMoney = createAsyncThunk(
  'wallet/sendMoney',
  async ({ senderId, receiverId, amount }, { rejectWithValue }) => {
    try {
      const response = await axios.post(`${API_URL}/transaction`, {
        senderId,
        receiverId,
        amount
      });
      return response.data;
    } catch (error) {
      return rejectWithValue(error.response?.data?.message || 'Transaction failed');
    }
  }
);

export const fetchTransactions = createAsyncThunk(
  'wallet/fetchTransactions',
  async (customerId, { rejectWithValue }) => {
    try {
      const response = await axios.get(`${API_URL}/transaction?customerId=${customerId}`);
      return response.data;
    } catch (error) {
      return rejectWithValue(error.response?.data?.message || 'Failed to fetch transactions');
    }
  }
);

const initialState = {
  balance: 5000, // Mock balance for UI preview
  transactions: [],
  status: 'idle', // idle | loading | succeeded | failed
  transactionStatus: 'idle',
  error: null,
  transactionError: null,
  recentReward: null, // To show kafka reward event notification
};

const walletSlice = createSlice({
  name: 'wallet',
  initialState,
  reducers: {
    clearTransactionStatus: (state) => {
      state.transactionStatus = 'idle';
      state.transactionError = null;
    },
    setRecentReward: (state, action) => {
      state.recentReward = action.payload;
    },
    clearRecentReward: (state) => {
      state.recentReward = null;
    }
  },
  extraReducers: (builder) => {
    builder
      // Fetch Balance
      .addCase(fetchBalance.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(fetchBalance.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.balance = action.payload.balance || action.payload;
      })
      .addCase(fetchBalance.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload;
      })
      // Send Money
      .addCase(sendMoney.pending, (state) => {
        state.transactionStatus = 'loading';
      })
      .addCase(sendMoney.fulfilled, (state, action) => {
        state.transactionStatus = 'succeeded';
        // Check if the API response indicates a reward is pending
        if (action.payload.rewardStatus === 'PENDING') {
           state.recentReward = { amount: 0, status: 'PENDING' };
        } else if (action.payload.rewardAmount > 0) {
           state.recentReward = { amount: action.payload.rewardAmount, status: action.payload.rewardStatus };
        }
      })
      .addCase(sendMoney.rejected, (state, action) => {
        state.transactionStatus = 'failed';
        state.transactionError = action.payload;
      })
      // Fetch Transactions
      .addCase(fetchTransactions.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(fetchTransactions.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.transactions = action.payload;
      })
      .addCase(fetchTransactions.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload;
      });
  },
});

export const { clearTransactionStatus, setRecentReward, clearRecentReward } = walletSlice.actions;

export default walletSlice.reducer;
