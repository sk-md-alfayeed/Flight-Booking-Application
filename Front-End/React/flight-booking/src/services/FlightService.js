import axios from "axios";

//Right Now global API gateway "http://localhost:9090"is not stable, thats why I am using original ports

//Flight Search Microservice
const FLIGHT_SEARCH_REST_API_URL = "http://localhost:8081/flight";

//Flight Search Microservice
const FLIGHT_SEARCH_AIRLINE_REST_API_URL =
  "http://localhost:8081/flight/airline";

//Flight Search Microservice
const FLIGHT_SEARCH_AIRPORT_REST_API_URL =
  "http://localhost:8081/flight/airport";

//Flight Fare Microservice
const FLIGHT_FARE_REST_API_URL = "http://localhost:8082/fare";

//Flight Book Microservice
const FLIGHT_BOOK_REST_API_URL = "http://localhost:8083/booking";

//Flight Book Microservice
const FLIGHT_CHECKIN_REST_API_URL = "http://localhost:8084/checkin";

//Header Generator
const generateHeader = (backEndToken) => {
  let header = {
    headers: {
      Authorization: "Bearer " + backEndToken,
    },
  };
  return header;
};

class FlightSearchService {
  //Flight Search Microservice
  getFlights(search) {
    return axios.post(FLIGHT_SEARCH_REST_API_URL + "/flights", search);
  }

  getAllFlights() {
    return axios.get(FLIGHT_SEARCH_REST_API_URL + "/allFlights");
  }
  getFlight(flightId) {
    return axios.get(FLIGHT_SEARCH_REST_API_URL + "/getFlight/" + flightId);
  }
  addFlight(flight, backEndTokenFlight) {
    return axios.post(
      FLIGHT_SEARCH_REST_API_URL + "/addFlight",
      flight,
      generateHeader(backEndTokenFlight)
    );
  }
  updateFlight(flight, backEndTokenFlight) {
    return axios.put(
      FLIGHT_SEARCH_REST_API_URL + "/updateFlight",
      flight,
      generateHeader(backEndTokenFlight)
    );
  }
  deleteFlight(flightId, backEndTokenFlight) {
    return axios.delete(
      FLIGHT_SEARCH_REST_API_URL + "/deleteFlight/" + flightId,
      generateHeader(backEndTokenFlight)
    );
  }
  //Airline
  getAllAirlines() {
    return axios.get(FLIGHT_SEARCH_AIRLINE_REST_API_URL + "/allAirlines");
  }
  getAirline(airlineId) {
    return axios.get(
      FLIGHT_SEARCH_AIRLINE_REST_API_URL + "/getAirline/" + airlineId
    );
  }
  addAirline(airline, backEndTokenFlight) {
    return axios.post(
      FLIGHT_SEARCH_AIRLINE_REST_API_URL + "/addAirline",
      airline,
      generateHeader(backEndTokenFlight)
    );
  }
  updateAirline(airline, backEndTokenFlight) {
    return axios.put(
      FLIGHT_SEARCH_AIRLINE_REST_API_URL + "/updateAirline",
      airline,
      generateHeader(backEndTokenFlight)
    );
  }
  deleteAirline(airlineId, backEndTokenFlight) {
    return axios.delete(
      FLIGHT_SEARCH_AIRLINE_REST_API_URL + "/deleteAirline/" + airlineId,
      generateHeader(backEndTokenFlight)
    );
  }
  //Airport
  getAllAirports() {
    return axios.get(FLIGHT_SEARCH_AIRPORT_REST_API_URL + "/allAirports");
  }
  getAirport(airportId) {
    return axios.get(
      FLIGHT_SEARCH_AIRPORT_REST_API_URL + "/getAirport/" + airportId
    );
  }
  addAirport(airport, backEndTokenFlight) {
    return axios.post(
      FLIGHT_SEARCH_AIRPORT_REST_API_URL + "/addAirport",
      airport,
      generateHeader(backEndTokenFlight)
    );
  }
  updateAirport(airport, backEndTokenFlight) {
    return axios.put(
      FLIGHT_SEARCH_AIRPORT_REST_API_URL + "/updateAirport",
      airport,
      generateHeader(backEndTokenFlight)
    );
  }
  deleteAirport(airportId, backEndTokenFlight) {
    return axios.delete(
      FLIGHT_SEARCH_AIRPORT_REST_API_URL + "/deleteAirport/" + airportId,
      generateHeader(backEndTokenFlight)
    );
  }

  //Flight Fare Microservice
  getAllFares() {
    return axios.get(FLIGHT_FARE_REST_API_URL + "/allFares");
  }
  getFare(flightId) {
    return axios.get(FLIGHT_FARE_REST_API_URL + "/getFare/" + flightId);
  }
  addFare(fare, backEndTokenFare) {
    return axios.post(
      FLIGHT_FARE_REST_API_URL + "/addFare",
      fare,
      generateHeader(backEndTokenFare)
    );
  }
  updateFare(fare, backEndTokenFare) {
    return axios.put(
      FLIGHT_FARE_REST_API_URL + "/updateFare",
      fare,
      generateHeader(backEndTokenFare)
    );
  }
  deleteFare(id, backEndTokenFare) {
    return axios.delete(
      FLIGHT_FARE_REST_API_URL + "/deleteFare/" + id,
      generateHeader(backEndTokenFare)
    );
  }

  //Flight Book Microservice
  getAllBookings(backEndTokenBooking) {
    return axios.get(
      FLIGHT_BOOK_REST_API_URL + "/allBookings",
      generateHeader(backEndTokenBooking)
    );
  }
  getBooking(bookingId, backEndTokenBookin) {
    return axios.get(
      FLIGHT_BOOK_REST_API_URL + "/getBooking/" + bookingId,
      generateHeader(backEndTokenBookin)
    );
  }
  updateBooking(booking, backEndTokenBooking) {
    return axios.put(
      FLIGHT_BOOK_REST_API_URL + "/updateBooking",
      booking,
      generateHeader(backEndTokenBooking)
    );
  }
  cancelBooking(booking, backEndTokenBooking) {
    return axios.put(
      FLIGHT_BOOK_REST_API_URL + "/updateBooking",
      booking,
      generateHeader(backEndTokenBooking)
    );
  }
  deleteBooking(bookingId, backEndTokenBooking) {
    return axios.delete(
      FLIGHT_BOOK_REST_API_URL + "/deleteBooking/" + bookingId,
      generateHeader(backEndTokenBooking)
    );
  }
  getSelectedFlight(flightId, backEndTokenBooking) {
    return axios.get(
      FLIGHT_BOOK_REST_API_URL + "/getFlight/" + flightId,
      generateHeader(backEndTokenBooking)
    );
  }
  bookFlight(booking, backEndTokenBooking) {
    return axios.post(
      FLIGHT_BOOK_REST_API_URL + "/addBooking",
      booking,
      generateHeader(backEndTokenBooking)
    );
  }

  getBookingsByEmail(email, backEndTokenBooking) {
    return axios.get(
      FLIGHT_BOOK_REST_API_URL + "/getBookingsByEmail/" + email,
      generateHeader(backEndTokenBooking)
    );
  }

  //Flight Check-In Microservice
  getCheckIn(search, backEndTokenCheckIn) {
    return axios.post(
      FLIGHT_CHECKIN_REST_API_URL + "/getCheckIn",
      search,
      generateHeader(backEndTokenCheckIn)
    );
  }

  getAllCheckIns(backEndTokenCheckIn) {
    return axios.get(
      FLIGHT_CHECKIN_REST_API_URL + "/allCheckIns",
      generateHeader(backEndTokenCheckIn)
    );
  }
  getCheckInById(checkInId, backEndTokenCheckIn) {
    return axios.get(
      FLIGHT_CHECKIN_REST_API_URL + "/getCheckInById/" + checkInId,
      generateHeader(backEndTokenCheckIn)
    );
  }
  addCheckIn(checkIn, backEndTokenCheckIn) {
    return axios.post(
      FLIGHT_CHECKIN_REST_API_URL + "/addCheckIn",
      checkIn,
      generateHeader(backEndTokenCheckIn)
    );
  }
  updateCheckIn(checkIn, backEndTokenCheckIn) {
    return axios.put(
      FLIGHT_CHECKIN_REST_API_URL + "/updateCheckIn/",
      checkIn,
      generateHeader(backEndTokenCheckIn)
    );
  }
}

export default new FlightSearchService();
