import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchBalance, fetchTransactions } from '../store/walletSlice';
import Navbar from './Navbar';
import SendMoney from './SendMoney';
import TransactionHistory from './TransactionHistory';
import { AlertCircle, CheckCircle2, IndianRupee, BellRing } from 'lucide-react';
import '../styles/Dashboard.css';

const Dashboard = () => {
  const dispatch = useDispatch();
  const { balance, status, error, recentReward } = useSelector((state) => state.wallet);
  // Fallbacks for demo UI
  const mockBalance = 5000;
  
  // Assume mock customer id for demo
  const customerId = 1; 

  useEffect(() => {
    // Uncomment these in production with proper endpoints
    // dispatch(fetchBalance(customerId));
    // dispatch(fetchTransactions(customerId));
  }, [dispatch, customerId]);

  return (
    <div className="dashboard-container">
      <Navbar />
      
      <main className="dashboard-main">
        <header className="dashboard-header">
          <div className="greeting">
            <h1>Welcome back, <span className="highlight-text">User</span></h1>
            <p>Manage your transactions and rewards in one place.</p>
          </div>
          <div className="balance-card glass-effect">
            <p className="balance-label">Total Balance</p>
            <h2 className="balance-value">
              <IndianRupee className="rupee-icon" /> 
              {status === 'succeeded' && balance !== undefined ? balance.toLocaleString('en-IN') : mockBalance.toLocaleString('en-IN')}
            </h2>
          </div>
        </header>

        {recentReward && (
          <div className={`reward-banner ${recentReward.status === 'PENDING' ? 'pending' : 'success'}`}>
            {recentReward.status === 'PENDING' ? (
              <>
                <BellRing className="banner-icon animate-pulse" />
                <span>You might receive a random reward up to ₹10 for this transaction! Checking...</span>
              </>
            ) : (
              <>
                <CheckCircle2 className="banner-icon" />
                <span>Congratulations! You earned a reward of ₹{recentReward.amount} from Kafka Events!</span>
              </>
            )}
          </div>
        )}

        <div className="content-grid">
          <div className="left-panel">
            <SendMoney />
          </div>
          <div className="right-panel">
            <TransactionHistory />
          </div>
        </div>
      </main>
    </div>
  );
};

export default Dashboard;
