export default function validate(values) {
  let errors = {};

  if (!values.airlineNo) {
    errors.airlineNo = "airlineNo is required";
  }

  if (!values.departureAirportCode) {
    errors.departureAirportCode = "departureAirportCode is required";
  } else if (values.departureAirportCode === values.destinationAirportCode) {
    errors.departureAirportCode =
      "departureAirportCode should be different from destinationAirportCode";
  }
  if (!values.destinationAirportCode) {
    errors.destinationAirportCode = "destinationAirportCode is required";
  } else if (values.destinationAirportCode === values.departureAirportCode) {
    errors.departureAirportCode =
      " destinationAirportCode should be different from departureAirportCode";
  }
  if (!values.departureDate) {
    errors.departureDate = "departureDate is required";
  }
  if (!values.arrivalDate) {
    errors.arrivalDate = "arrivalDate is required";
  }
  if (!values.departureTime) {
    errors.departureTime = "departureTime is required";
  }
  if (!values.arrivalTime) {
    errors.arrivalTime = "arrivalTime is required";
  }
  if (!values.seats) {
    errors.seats = "seats are required";
  }
  return errors;
}
