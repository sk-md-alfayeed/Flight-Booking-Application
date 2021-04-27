export default function validate(values) {
  let errors = {};

  if (!values.username) {
    errors.username = "Username is required";
  } else if (values.username.length < 4) {
    errors.username = "Username must be 4 or more characters";
  }

  if (!values.password) {
    errors.password = "Password is required";
  } else if (values.password.length < 4) {
    errors.password = "Password must be 4 or more characters";
  }
  return errors;
}
