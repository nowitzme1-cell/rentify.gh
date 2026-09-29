# Rentify.pk Deployment Guide for InfinityFree (htdocs) & Hostinger (public_html)

Rentify.pk is completely built and ready for production deployment on PHP/Apache cPanel hosting like **Hostinger**, **InfinityFree**, **cPanel**, **Namecheap**, or **GoDaddy**.

---

## Quick Deploy (Option A: Uploading the Pre-Built `dist` Folder)

The `dist/` directory in this project contains the standalone production assets:
- `dist/index.html`
- `dist/assets/index-*.js`
- `dist/assets/index-*.css`
- `dist/.htaccess` (included for Apache URL rewriting)

### For Hostinger:
1. Log in to your Hostinger hPanel.
2. Go to **File Manager** -> **public_html**.
3. Upload all files from inside the `dist/` folder directly into `public_html/`.
4. Ensure `.htaccess` is uploaded into `public_html/`.
5. Visit `https://yourdomain.com` - Rentify.pk will be live!

### For InfinityFree:
1. Log in to your InfinityFree control panel.
2. Open **Online File Manager** or connect via FTP (FileZilla).
3. Navigate into the `htdocs/` directory.
4. Upload all files and folders from inside `dist/` into `htdocs/`.
5. Ensure `.htaccess` is in `htdocs/`.
6. Visit your free domain (`yourname.infinityfreeapp.com`).

---

## Building from Source (Option B)

If you have Node.js installed locally on your computer:
```bash
npm install
npm run build
```
This will recreate the `dist/` folder with updated assets anytime you make code changes.

---

## Features Implemented:
1. **Camera Example Focus**: Salman Khan (Photographer in Kashmore, Rs. 2,000/day Sony A6400) and Bilal Ahmad (Cousin needing camera for sister's wedding).
2. **Phase 1 Target Cities**: Kashmore, Kandhkot, Usta Muhammad, Sui, Dera Allah Yar, Jacobabad, Shikarpur, and All Pakistan.
3. **8 Core Rental Categories**: Cameras, ACs, Furniture, Cars, Tools, Generators, Wedding, Dresses.
4. **10% Commission Model**: Auto calculated with refundable deposits and cash handover receipts.
5. **Modern 2025 Airbnb + Apple + Linear Design System**:
   - Ultra-clean aesthetic, #0F0F0F pure black pill buttons, #635BFF purple brand accent, soft 20px glass cards, Instagram verified blue tick #0095F6.
   - Floating glass bottom navigation with elevated + post button.
   - Live in-app chat with system status notifications.
   - CNIC Blue Tick verification portal.
   - Admin Panel at `/admin` (password: `admin123`).
