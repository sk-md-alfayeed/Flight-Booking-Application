import "./App.css";
import React from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";

import ProtectedRoute from "./components/global/ProtectedRoute";

import AddOrUpdateAirline from "./components/admin/airline/AddOrUpdateAirline";
import AddOrUpdateAirport from "./components/admin/airport/AddOrUpdateAirport";
import UserBooking from "./components/user/UserBooking";
import UserProfile from "./components/user/UserProfile";
import UserRoute from "./components/global/UserRoute";
import Header from "./components/global/Header";
import Home from "./components/global/Home";
import Footer from "./components/global/Footer";
import Register from "./components/auth/Register";
import ManageFare from "./components/admin/fare/ManageFare";
import ManageFlight from "./components/admin/flight/ManageFlight";
import AdminDashboard from "./components/admin/AdminDashboard";
import Login from "./components/auth/Login";

import FlightSearch from "./components/user/FlightSearch";

import UserDashboard from "./components/user/UserDashboard";
import FlightBook from "./components/user/FlightBook";
import UpdateBooking from "./components/admin/booking/UpdateBooking";
import ManageBooking from "./components/admin/booking/ManageBooking";
import Acknowledgment from "./components/user/Acknowledgment";
// import Log from "./components/auth/Login";
// import Reg from "./components/auth/Register";
import CheckIn from "./components/user/CheckIn";

function App() {
  return (
    <div>
      <Router>
        <Header />
        <br></br>
        <div className="container">
          <Switch>
            {/* GLOBAL */}

            <Route path="/" exact component={Home}></Route>
            <Route path="/home" component={Home}></Route>
            <Route path="/flights" component={FlightSearch}></Route>
            <Route path="/login" component={Login}></Route>
            {/* <Route path="/login" component={Log}></Route> */}
            <Route path="/register" component={Register}></Route>
            {/* <Route path="/register" component={Reg}></Route> */}

            <Route path="/checkin" component={CheckIn}></Route>
            {/* <Route path="/checkin/:id" component={CheckIn}></Route> */}

            {/* USER */}

            <UserRoute path="/user_profile" component={UserProfile}></UserRoute>
            <UserRoute path="/user_booking" component={UserBooking}></UserRoute>
            <UserRoute
              path="/user_dashboard"
              component={UserDashboard}
            ></UserRoute>
            <UserRoute path="/booking/:id" component={FlightBook}></UserRoute>
            <UserRoute
              path="/acknowledgment/:id"
              component={Acknowledgment}
            ></UserRoute>

            {/* ADMIN */}

            <ProtectedRoute
              path="/admin_dashboard"
              component={AdminDashboard}
            ></ProtectedRoute>
            {/* Flight */}
            <ProtectedRoute
              path="/manage_flight"
              component={ManageFlight}
            ></ProtectedRoute>

            {/* Fare */}
            <ProtectedRoute
              path="/manage_fare"
              component={ManageFare}
            ></ProtectedRoute>

            {/* Booking */}
            <ProtectedRoute
              path="/manage_booking"
              component={ManageBooking}
            ></ProtectedRoute>
            <UserRoute
              path="/update_booking/:id"
              component={UpdateBooking}
            ></UserRoute>
            {/* Airline */}
            <ProtectedRoute
              path="/add_or_update_airline/:id"
              component={AddOrUpdateAirline}
            ></ProtectedRoute>
            {/* Airport */}
            <ProtectedRoute
              path="/add_or_update_airport/:id"
              component={AddOrUpdateAirport}
            ></ProtectedRoute>
          </Switch>
        </div>
        <Footer />
      </Router>
    </div>
  );
}

export default App;
