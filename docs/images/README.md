# Slike za dokumentaciju

Ovde idu snimci ekrana. `DOKUMENTACIJA.docx` ih preuzima automatski, po imenu fajla.

Format: PNG. Ime mora biti tačno kao u tabeli.

## Obavezne — bez njih u dokumentu stoji označeno prazno mesto

| Ime fajla | Šta snimiti | Gde |
|---|---|---|
| `ci-zeleno.png` | CI pipeline sa svim zelenim job-ovima | github.com/LukaC011/devOps → Actions → CI Pipeline → poslednji run nad `main` |
| `cd-zeleno.png` | CD pipeline, 5 build job-ova + post-deploy-check | Actions → CD Pipeline → poslednji run nad `main` |
| `sonarcloud.png` | Quality Gate Passed i procenat pokrivenosti | sonarcloud.io → projekat `LukaC011_devOps` |

## Opcione — bez njih ostaje tekstualni dokaz, bez praznog okvira

| Ime fajla | Šta snimiti | Gde |
|---|---|---|
| `zipkin-trace.png` | Trace sa 7 spanova kroz 4 servisa | localhost:9411 → Run Query → servis `task-service` |
| `loki-logovi.png` | Logovi dva servisa sa istim `traceId` | localhost:3000 → Explore → Loki |
| `docker-ps.png` | Svi kontejneri u statusu healthy | terminal, `docker compose ps` |
| `post-deploy.png` | Log post-deploy-check job-a | Actions → CD Pipeline → job `post-deploy-check` |
| `e2e-testovi.png` | 13 E2E testova prolazi | Actions → CI Pipeline → job `e2e` |

## Posle dodavanja slika

Javi da su slike na mestu i dokument se regeneriše. Slike ulaze same, na prava mesta.
