import React, { useState } from 'react';

const TransactionManage = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [transaction, setTransaction] = useState(null);
  const [error, setError] = useState('');

  // Simulated transaction data (can be replaced with API calls later)
  const transactionData = [
    {
      id: 'T12345',
      user: 'John Doe',
      book: 'Harry Potter and the Sorcerer\'s Stone',
      dueDate: '2024-10-10',
      fines: 10
    },
    {
      id: 'T67890',
      user: 'Jane Smith',
      book: 'The Lord of the Rings',
      dueDate: '2024-11-15',
      fines: 0
    }
  ];

  const handleSearch = () => {
    const result = transactionData.find(trx => trx.id === searchTerm.trim());

    if (result) {
      setTransaction(result);
      setError('');
    } else {
      setTransaction(null);
      setError('Transaction ID not found.');
    }
  };

  return (
    <div className="max-w-2xl mx-auto p-8">
      <h1 className="text-3xl font-semibold text-center text-gray-800 mb-6">Transaction Search</h1>

      {/* Search Bar */}
      <div className="flex items-center mb-8">
        <input
          type="text"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          placeholder="Enter Transaction ID..."
          className="flex-grow p-3 border rounded-l-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
        <button
          onClick={handleSearch}
          className="bg-blue-600 text-white p-3 rounded-r-lg hover:bg-blue-700 transition"
        >
          Search
        </button>
      </div>

      {/* Error Message */}
      {error && <p className="text-red-600 text-center mb-4">{error}</p>}

      {/* Transaction Result */}
      {transaction && (
        <div className="bg-white shadow-lg rounded-lg p-6 space-y-4">
          <h2 className="text-xl font-bold text-gray-800">Transaction Details</h2>
          <div className="border-b pb-4">
            <p className="text-gray-700">
              <span className="font-semibold">Transaction ID:</span> {transaction.id}
            </p>
            <p className="text-gray-700">
              <span className="font-semibold">User:</span> {transaction.user}
            </p>
            <p className="text-gray-700">
              <span className="font-semibold">Book:</span> {transaction.book}
            </p>
            <p className="text-gray-700">
              <span className="font-semibold">Due Date:</span> {transaction.dueDate}
            </p>
            <p className="text-gray-700">
              <span className="font-semibold">Fines:</span> {transaction.fines > 0 ? `$${transaction.fines}` : 'No fines'}
            </p>
          </div>
        </div>
      )}
    </div>
  );
};

export default TransactionManage;
