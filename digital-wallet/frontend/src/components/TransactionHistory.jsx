import React from 'react';
import { useSelector } from 'react-redux';
import { IndianRupee, ArrowDownLeft, ArrowUpRight, Clock, CheckCircle } from 'lucide-react';
import '../styles/TransactionHistory.css';

const TransactionHistory = () => {
  const { transactions, status } = useSelector((state) => state.wallet);

  // Mock data for UI preview
  const mockTransactions = [
    { id: 1, type: 'CREDIT', amount: 500, date: '2026-03-14T10:30:00Z', status: 'SUCCESS', description: 'Received from Alice' },
    { id: 2, type: 'DEBIT', amount: 150, date: '2026-03-13T14:45:00Z', status: 'SUCCESS', description: 'Sent to Bob' },
    { id: 3, type: 'REWARD', amount: 8, date: '2026-03-12T09:15:00Z', status: 'SUCCESS', description: 'Kafka Transaction Reward' },
    { id: 4, type: 'DEBIT', amount: 1000, date: '2026-03-10T11:20:00Z', status: 'SUCCESS', description: 'Sent to Charlie' },
    { id: 5, type: 'CREDIT', amount: 50, date: '2026-03-10T08:00:00Z', status: 'SUCCESS', description: 'Refund from Merchant' },
  ];

  const displayTransactions = (status === 'succeeded' && transactions.length > 0) ? transactions : mockTransactions;

  return (
    <div className="history-card glass-effect">
      <div className="history-header">
        <h3>Recent Transactions</h3>
        <button className="view-all-btn">View All</button>
      </div>

      <div className="transaction-list">
        {displayTransactions.map((tx) => (
          <div key={tx.id} className="transaction-item">
            <div className={`tx-icon ${tx.type.toLowerCase()}`}>
              {tx.type === 'CREDIT' || tx.type === 'REWARD' ? <ArrowDownLeft size={20} /> : <ArrowUpRight size={20} />}
            </div>
            
            <div className="tx-details">
              <h4>{tx.description || tx.type}</h4>
              <p className="tx-date">
                <Clock size={12} className="inline-icon" />
                {new Date(tx.date).toLocaleString('en-IN', { 
                  month: 'short', day: 'numeric', hour: '2-digit', minute: '2-digit' 
                })}
              </p>
            </div>
            
            <div className={`tx-amount ${tx.type.toLowerCase()}`}>
              {tx.type === 'CREDIT' || tx.type === 'REWARD' ? '+' : '-'}
              <IndianRupee size={14} className="inline-icon" />
              {tx.amount.toLocaleString('en-IN', { minimumFractionDigits: 2 })}
              <div className="tx-status">
                {tx.status === 'SUCCESS' ? <CheckCircle size={12} className="success-icon" /> : tx.status}
              </div>
            </div>
          </div>
        ))}

        {displayTransactions.length === 0 && (
          <div className="empty-state">
            <h4 className="empty-text">No recent transactions found.</h4>
          </div>
        )}
      </div>
    </div>
  );
};

export default TransactionHistory;
