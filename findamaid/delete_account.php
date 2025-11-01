<?php
// Include database connection if needed
include 'con.php';

// Function to display the delete account form
function display_delete_account_form() {
    echo '<!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Delete Account - Find a Maid</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                line-height: 1.6;
                margin: 0;
                padding: 0;
                background-color: #f4f4f4;
                color: #333;
            }
            .container {
                max-width: 800px;
                margin: 50px auto;
                padding: 20px;
                background: #fff;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                border-radius: 8px;
            }
            h1 {
                text-align: center;
                color: #d9534f;
                margin-bottom: 20px;
            }
            p {
                color: #555;
                margin-bottom: 20px;
                text-align: center;
            }
            form {
                margin-top: 20px;
            }
            label {
                display: block;
                margin-bottom: 10px;
                font-weight: bold;
            }
            input[type="text"], input[type="email"], input[type="password"] {
                width: 100%;
                padding: 10px;
                margin-bottom: 20px;
                border: 1px solid #ccc;
                border-radius: 4px;
            }
            input[type="submit"] {
                padding: 12px 20px;
                background-color: #d9534f;
                border: none;
                color: white;
                border-radius: 4px;
                cursor: pointer;
                font-size: 16px;
            }
            input[type="submit"]:hover {
                background-color: #c9302c;
            }
        </style>
        <script>
            function confirmDeletion(event) {
                event.preventDefault(); 
                if (confirm("⚠️ Your Find a Maid account will be deleted within 24 hours. Do you wish to proceed?")) {
                    document.getElementById("deleteForm").submit();
                }
            }
        </script>
    </head>
    <body>
        <div class="container">
            <h1>Delete Account</h1>
            <p>
                If you wish to delete your <strong>Find a Maid</strong> account, please fill out the form below. 
                <br>⚠️ This action is permanent and cannot be undone.
            </p>
            <form id="deleteForm" action="delete_account.php" method="post" onsubmit="confirmDeletion(event)">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required>
                
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
                
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
                
                <input type="submit" value="Delete Account">
            </form>
        </div>
    </body>
    </html>';
}

// Call the function to display the delete account form
display_delete_account_form();
?>
