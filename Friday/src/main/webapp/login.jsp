<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login | Friday</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: #f8f9fa;
            display: flex;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
        }
        .card {
            border-radius: 1rem;
        }
        .form-control:focus {
            box-shadow: none;
            border-color: #198754;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-12 col-md-5 col-lg-4">
                <div class="card shadow p-4">
                    <h3 class="text-center mb-4 fw-bold">Login</h3>

                    <form action="login" method="post">
                        <div class="mb-3">
                            <label class="form-label">Username</label>
                            <input class="form-control" type="text" name="username" placeholder="Enter username" required/>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Password</label>
                            <input class="form-control" type="password" name="password" placeholder="Enter password" required/>
                        </div>

                        <button class="btn btn-success w-100 fw-bold" type="submit">Login</button>
                    </form>

                    <div class="mt-3 text-center">
                        Don’t have an account? <a href="register.jsp" class="text-decoration-none">Register</a>
                    </div>

                    <% if (request.getAttribute("error") != null) { %>
                        <div class="alert alert-danger mt-3 text-center">
                            <%= request.getAttribute("error") %>
                        </div>
                    <% } %>

                    <!-- Optional: Google login button -->
                    <!--
                    <div class="mt-3 text-center">
                        <a href="google-login" class="btn btn-outline-danger w-100">
                            <i class="bi bi-google"></i> Login with Google
                        </a>
                    </div>
                    -->
                </div>
            </div>
        </div>
    </div>
</body>
</html>
