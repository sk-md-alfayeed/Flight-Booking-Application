export default function validate(values) {
  let errors = {};

  if (!values.pnrNo) {
    errors.pnrNo = "PNR No is required";
  }
  if (!values.email) {
    errors.email = "Email address is required";
  } else if (!/\S+@\S+\.\S+/.test(values.email)) {
    errors.email = "Email address is invalid";
  }

  return errors;
}
