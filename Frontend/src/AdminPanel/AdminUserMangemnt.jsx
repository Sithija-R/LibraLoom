import React, { useState } from "react";

const UserManage = () => {
  const [searchTerm, setSearchTerm] = useState("");
  const [users, setUsers] = useState([
    { id: 1, name: "John Doe" },
    { id: 2, name: "Jane Smith" },
    { id: 3, name: "Alice Johnson" },
    // Add more users here as needed
  ]);

  const handleDelete = (id) => {
    // Handle user deletion
    setUsers(users.filter(user => user.id !== id));
  };

  const filteredUsers = users.filter(user => 
    user.name.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="p-4">
      <h2 className="text-2xl font-bold mb-4">User Management</h2>

      {/* Search Bar */}
      <input
        type="text"
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
        placeholder="Search users..."
        className="w-full p-2 border border-gray-300 rounded-md mb-4"
      />

      {/* User List  */}
      <div className="h-[300px] overflow-y-scroll border border-gray-300 rounded-md p-2">
        {filteredUsers.map(user => (
          <div key={user.id} className="flex justify-between items-center p-2 border-b">
            <span>{user.name}</span>
            <button 
              className="bg-red-500 text-white px-2 py-1 rounded-md"
              onClick={() => handleDelete(user.id)}
            >
              Delete
            </button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default UserManage;
