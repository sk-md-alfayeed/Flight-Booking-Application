export default function validate(values) {
  let errors = {};

  if (!values.flightFare) {
    errors.flightFare = "flightFare is required";
  } else if (values.flightFare < 3000) {
    errors.flightFare = "flightFare should be minimum 3000";
  }

  return errors;
}
