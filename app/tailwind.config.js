/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {
      colors: {
        nerd: {
          50: "#171212"
        }
      }
    },
  },
  plugins: [],
}