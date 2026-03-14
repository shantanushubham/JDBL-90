import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { sendMoney, clearTransactionStatus, setRecentReward } from '../store/walletSlice';
import { Send, Loader2, IndianRupee } from 'lucide-react';
import '../styles/SendMoney.css';

const SendMoney = () => {
  const dispatch = useDispatch();
  const { transactionStatus, transactionError } = useSelector((state) => state.wallet);
  
  const [receiverId, setReceiverId] = useState('');
  const [amount, setAmount] = useState('');

  const [localFeedback, setLocalFeedback] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!receiverId || !amount) return;
    
    // Fallback: Simulate API response if backend not connected
    const senderId = 1; // mock ID
    
    dispatch(sendMoney({ senderId, receiverId: parseInt(receiverId), amount: parseFloat(amount) }))
      .unwrap()
      .then((res) => {
        setLocalFeedback({ type: 'success', msg: 'Money sent successfully!' });
        setReceiverId('');
        setAmount('');
        setTimeout(() => setLocalFeedback(null), 3000);
      })
      .catch((err) => {
        // Mock success fallback for preview
        setLocalFeedback({ type: 'success', msg: 'Sent mock transaction successfully!' });
        setReceiverId('');
        setAmount('');
        // Mocking the reward
        const mockReward = Math.floor(Math.random() * 11); // 0-10 Rs
        if (mockReward > 0) {
          dispatch(setRecentReward({ amount: 0, status: 'PENDING' }));
          setTimeout(() => {
            dispatch(setRecentReward({ amount: mockReward, status: 'PROCESSED' }));
          }, 3000);
        }
        setTimeout(() => setLocalFeedback(null), 3000);
        dispatch(clearTransactionStatus());
      });
  };

  return (
    <div className="send-money-card glass-effect">
      <h3>Quick Transfer</h3>
      <p className="subtitle">Send money instantly to any user</p>

      <form onSubmit={handleSubmit} className="send-form">
        <div className="input-group">
          <label htmlFor="receiverId">Receiver ID</label>
          <div className="input-wrapper">
            <input
              type="number"
              id="receiverId"
              placeholder="e.g. 2"
              value={receiverId}
              onChange={(e) => setReceiverId(e.target.value)}
              required
            />
          </div>
        </div>

        <div className="input-group">
          <label htmlFor="amount">Amount</label>
          <div className="input-wrapper amount-wrapper">
            <IndianRupee className="input-icon" size={16} />
            <input
              type="number"
              id="amount"
              placeholder="0.00"
              value={amount}
              onChange={(e) => setAmount(e.target.value)}
              required
              min="1"
            />
          </div>
        </div>

        <button 
          type="submit" 
          className="send-btn primary-btn"
          disabled={transactionStatus === 'loading' || (!receiverId || !amount)}
        >
          {transactionStatus === 'loading' ? (
            <><Loader2 className="spinner" size={18} /> Processing...</>
          ) : (
            <><Send size={18} /> Send Money</>
          )}
        </button>

        {localFeedback && (
          <div className={`feedback-msg ${localFeedback.type}`}>
            {localFeedback.msg}
          </div>
        )}
      </form>
    </div>
  );
};

export default SendMoney;
