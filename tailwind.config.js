/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        rentify: {
          black: '#0F0F0F',
          dark: '#18181B',
          darkgrey: '#27272A',
          mediumgrey: '#3F3F46',
          zinc: '#71717A',
          lightgrey: '#A1A1AA',
          border: '#F0F0F0',
          borderdark: '#E4E4E7',
          surface: '#F4F4F5',
          bg: '#FAFAFA',
          purple: '#635BFF',
          verified: '#0095F6',
          green: '#00C950'
        }
      },
      fontFamily: {
        sans: ['Inter', 'system-ui', 'sans-serif'],
      },
      borderRadius: {
        '20px': '20px',
      },
      boxShadow: {
        'soft': '0px 2px 8px rgba(0,0,0,0.04)',
        'lift': '0px 8px 24px rgba(0,0,0,0.08)',
        'float': '0px 8px 32px rgba(0,0,0,0.12)',
      }
    },
  },
  plugins: [],
}
