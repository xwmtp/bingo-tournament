# Bingo Tournament Backend

## Setup

### Racetime

Spin up a local development environment of *racetime.gg* by following
[their instructions](https://github.com/racetimeGG/racetime-app#quick-setup-guide). Do create an admin user, it is
needed to set up the OAuth application. After launching the application navigate to http://localhost:8000/admin and
click on the "+" button in the line "Applications". Set the following options:

| Option                       | Value                                            |
|------------------------------|--------------------------------------------------|
| **Redirect uris**            | http://localhost:8080/login/oauth2/code/racetime |
| **Client type**              | Confidential                                     |
| **Authorization grant type** | Authorization code                               |
| **Name**                     | OoT Bingo Tournament                             |

Save and take a note with the generated *Client id* and *Client secret*.

Create a file called `src/main/resources/application-local.yaml` with the following content (replacing the placeholders
with the data from your local racetime instance).

```yaml
bingo:
  users:
    admins:
      - <your admin user's ID>

spring:
  security:
    oauth2:
      client:
        registration:
          racetime:
            client-id: <your client id>
            client-secret: <your client secret>
```

### Local Database

In the root directory of this repository, run

```shell
docker-compose up -d
```

:warning: **Docker volumes on Windows** :warning:

When working on the Windows file system the Docker volume can't be created with the correct permissions, even when
running Docker in WSL2. To mitigate this issue a named volume has to be used. Create a file called
`docker-compose.override.yaml` with the following content:

```yaml
services:
  db:
    volumes:
      - "dev_db:/var/lib/postgresql/data"

volumes:
  dev_db:
```

### Run Configuration

Run your application with the argument `--spring.profiles.active=local`.

## Login

To authenticate call the endpoint `/login/racetime`. You will be redirected to your local racetime instance where you
can log in and allow access for the application.

## CSRF Protection

The endpoints are protected against CSRF. Therefore, all `POST`, `PUT`, and `PATCH` requests must send the contents of
the `XSRF-TOKEN` cookie as a header called `X-XSRF-TOKEN`.
