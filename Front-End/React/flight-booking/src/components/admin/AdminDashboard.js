import React from "react";
import { Link } from "react-router-dom";

function AdminDashboard() {
  return (
    <div>
      <h1 className="text-center">Admin Dashboard</h1>
      <div>
        <Link
          className="nav-link text-center"
          to={"/add_or_update_airline/add"}
        >
          Manage Airline
        </Link>
        <Link
          className="nav-link text-center"
          to={"/add_or_update_airport/add"}
        >
          Manage Airport
        </Link>
      </div>
    </div>
  );
}

export default AdminDashboard;
