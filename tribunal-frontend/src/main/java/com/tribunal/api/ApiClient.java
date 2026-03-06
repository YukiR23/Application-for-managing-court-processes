package com.tribunal.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tribunal.dto.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/** Clasa pentru comunicarea cu backend-ul prin API REST.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

public class ApiClient {

    private static final String BASE_URL = "http://localhost:8080/api";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public String login(String username, String password) {
        try {
            String body = """
                {
                  "username": "%s",
                  "password": "%s"
                }
                """.formatted(username, password);

            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/auth/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> resp =
                    client.send(req, HttpResponse.BodyHandlers.ofString());

            System.out.println("LOGIN STATUS = " + resp.statusCode());
            System.out.println("LOGIN BODY   = " + resp.body());

            return resp.body();

        } catch (Exception e) {
            e.printStackTrace();
            return "{\"success\": false}";
        }
    }

    // ================= PROCESE =================

    public List<ProcesDTO> getProcese() {
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/procese"))
                    .GET()
                    .build();

            String json = client.send(req, HttpResponse.BodyHandlers.ofString()).body();
            JsonNode root = mapper.readTree(json);

            List<ProcesDTO> list = new ArrayList<>();

            for (JsonNode p : root) {
                ProcesDTO dto = new ProcesDTO();

                dto.setIdProces(p.get("idProces").asInt());

                dto.setNrDosar(p.get("nrDosar").asText());
                dto.setMaterieJuridica(p.get("materieJuridica").asText());
                dto.setStadiuProces(p.get("stadiuProces").asText());
                dto.setDataInregistrare(p.get("dataInregistrare").asText());

                dto.setIdJudecator(
                        p.hasNonNull("idJudecator")
                                ? p.get("idJudecator").asInt()
                                : null
                );

                dto.setIdProcuror(
                        p.hasNonNull("idProcuror")
                                ? p.get("idProcuror").asInt()
                                : null
                );

                if (p.hasNonNull("judecator")) {
                    dto.setIdJudecator(
                            p.get("judecator").get("idJudecator").asInt()
                    );
                }


                if (p.hasNonNull("procuror")) {
                    dto.setIdProcuror(
                            p.get("procuror").get("idProcuror").asInt()
                    );
                }

                list.add(dto);
            }

            return list;

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<ProcesDTO> searchProcese(String q) {
        return getList("/procese/search?q=" + q, new TypeReference<>() {
        });
    }

    public void addProces(ProcesDTO p) {
        ObjectNode root = mapper.createObjectNode();

        ObjectNode jud = mapper.createObjectNode();
        jud.put("idJudecator", p.getIdJudecator());
        root.set("judecator", jud);

        if (p.getIdProcuror() != null) {
            ObjectNode proc = mapper.createObjectNode();
            proc.put("idProcuror", p.getIdProcuror());
            root.set("procuror", proc);
        }

        root.put("nrDosar", p.getNrDosar());
        root.put("materieJuridica", p.getMaterieJuridica());
        root.put("stadiuProces", p.getStadiuProces());
        root.put("dataInregistrare", p.getDataInregistrare());

        send("/procese", "POST", root);
    }

    public void updateProces(ProcesDTO p) {
        ObjectNode root = mapper.createObjectNode();

        ObjectNode jud = mapper.createObjectNode();
        jud.put("idJudecator", p.getIdJudecator());
        root.set("judecator", jud);

        if (p.getIdProcuror() != null) {
            ObjectNode proc = mapper.createObjectNode();
            proc.put("idProcuror", p.getIdProcuror());
            root.set("procuror", proc);
        } else {
            root.putNull("procuror");
        }

        root.put("nrDosar", p.getNrDosar());
        root.put("materieJuridica", p.getMaterieJuridica());
        root.put("stadiuProces", p.getStadiuProces());
        root.put("dataInregistrare", p.getDataInregistrare());

        send("/procese/" + p.getIdProces(), "PUT", root);
    }


    public void deleteProces(Integer idProces) {
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/procese/" + idProces))
                    .DELETE()
                    .build();

            HttpResponse<String> resp =
                    client.send(req, HttpResponse.BodyHandlers.ofString());

            if (resp.statusCode() >= 400) {
                throw new RuntimeException(
                        resp.body() != null && !resp.body().isBlank()
                                ? resp.body()
                                : "Eroare la ștergerea procesului"
                );
            }

        } catch (Exception e) {
            throw new RuntimeException("Ștergerea procesului a eșuat", e);
        }
    }


    public Map<String, Object> getJudecatorProces(Integer idProces) {
        try {
            String json = send(
                    "/procese/" + idProces + "/judecator",
                    "GET",
                    null
            );

            if (json == null || json.equals("{}")) {
                return Map.of();
            }

            return mapper.readValue(json, Map.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Object> getProcurorProces(Integer idProces) {
        try {
            String json = send(
                    "/procese/" + idProces + "/procuror",
                    "GET",
                    null
            );

            if (json == null || json.equals("{}")) {
                return Map.of();
            }

            return mapper.readValue(json, Map.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ================= SEDINTE =================

    public List<SedintaDTO> getSedinteByProces(Integer idProces) {
        try {
            String json = send("/sedinte/proces/" + idProces, "GET", null);
            JsonNode root = mapper.readTree(json);

            List<SedintaDTO> list = new ArrayList<>();

            for (JsonNode n : root) {
                SedintaDTO s = new SedintaDTO();

                s.setIdSedinta(n.get("idSedinta").asInt());
                s.setDataTermen(n.get("dataTermen").asText());
                s.setOra(n.get("ora").asText());
                s.setSala(n.get("sala").asText());
                s.setRezultat(n.get("rezultat").asText());

                s.setIdGrefier(n.get("idGrefier").asInt());
                s.setNumeGrefier(n.get("numeGrefier").asText());
                s.setPrenumeGrefier(n.get("prenumeGrefier").asText());

                list.add(s);
            }

            return list;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void addSedinta(SedintaDTO s) {
        try {
            ObjectNode root = mapper.createObjectNode();

            root.put("dataTermen", s.getDataTermen());
            root.put("ora", s.getOra());
            root.put("sala", s.getSala());
            root.put("rezultat", s.getRezultat());
            root.put("idGrefier", s.getIdGrefier());

            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/sedinte/proces/" + s.getIdProces()))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(root.toString()))
                    .build();

            HttpResponse<String> resp =
                    client.send(req, HttpResponse.BodyHandlers.ofString());

            if (resp.statusCode() >= 400) {
                throw new RuntimeException(resp.body());
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    public void updateSedinta(Integer idSedinta, SedintaDTO s) {
        try {
            ObjectNode root = mapper.createObjectNode();

            root.put("dataTermen", s.getDataTermen());
            root.put("ora", s.getOra());
            root.put("sala", s.getSala());
            root.put("rezultat", s.getRezultat());
            root.put("idGrefier", s.getIdGrefier());

            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/sedinte/" + idSedinta))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(root.toString()))
                    .build();

            HttpResponse<String> resp =
                    client.send(req, HttpResponse.BodyHandlers.ofString());

            if (resp.statusCode() >= 400) {
                throw new RuntimeException(resp.body());
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    public void deleteSedinta(Integer idSedinta) {
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/sedinte/" + idSedinta))
                    .DELETE()
                    .build();

            HttpResponse<String> resp =
                    client.send(req, HttpResponse.BodyHandlers.ofString());

            if (resp.statusCode() >= 400) {
                throw new RuntimeException(resp.body());
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    public List<JudecatorDTO> getJudecatori() {
        return getList("/judecatori", new TypeReference<List<JudecatorDTO>>() {
        });
    }

    public List<ProcurorDTO> getProcurori() {
        return getList("/procurori", new TypeReference<List<ProcurorDTO>>() {
        });
    }

    public List<GrefierDTO> getGrefieri() {
        return getList("/grefieri", new TypeReference<List<GrefierDTO>>() {
        });
    }

    public List<PersoanaDTO> getPersoane() {
        return getList("/persoane", new TypeReference<List<PersoanaDTO>>() {
        });
    }

    public List<AvocatDTO> getAvocati() {
        return getList("/avocati", new TypeReference<List<AvocatDTO>>() {
        });
    }

    // ================= PARTI =================
    public List<ParteDTO> getParti(Integer idProces) {
        try {
            String json = send("/parti/proces/" + idProces, "GET", null);
            JsonNode root = mapper.readTree(json);

            List<ParteDTO> result = new ArrayList<>();

            for (JsonNode n : root) {
                ParteDTO dto = new ParteDTO();

                dto.setIdParte(n.get("idParte").asInt());
                dto.setTipParte(n.get("tipParte").asText());

                dto.setIdPersoana(n.get("idPersoana").asInt());
                dto.setNumePersoana(n.get("numePersoana").asText());
                dto.setPrenumePersoana(n.get("prenumePersoana").asText());

                if (n.hasNonNull("idAvocat")) {
                    dto.setIdAvocat(n.get("idAvocat").asInt());
                    dto.setNumeAvocat(n.get("numeAvocat").asText());
                    dto.setPrenumeAvocat(n.get("prenumeAvocat").asText());
                }

                result.add(dto);
            }

            return result;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void addParte(ParteDTO p) {

        ObjectNode root = mapper.createObjectNode();

        root.put("idProces", p.getIdProces());
        root.put("idPersoana", p.getIdPersoana());
        root.put("tipParte", p.getTipParte());

        if (p.getIdAvocat() != null) {
            root.put("idAvocat", p.getIdAvocat());
        } else {
            root.putNull("idAvocat");
        }

        send("/parti", "POST", root);
    }

    public void deleteParte(Integer idParte) {
        send("/parti/" + idParte, "DELETE", null);
    }

    // ================= HOTARARI =================
    public List<HotarareDTO> getHotarariByProces(Integer idProces) {
        try {
            String json = send("/hotarari/proces/" + idProces, "GET", null);
            JsonNode root = mapper.readTree(json);

            List<HotarareDTO> list = new ArrayList<>();

            for (JsonNode n : root) {
                HotarareDTO dto = new HotarareDTO();

                dto.setIdHotarare(n.get("idHotarare").asInt());
                dto.setIdProces(n.get("idProces").asInt());
                dto.setTipHotarare(n.get("tipHotarare").asText());
                dto.setDataPronuntare(n.get("dataPronuntare").asText());

                list.add(dto);
            }

            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addHotarare(HotarareDTO h) {

        ObjectNode root = mapper.createObjectNode();

        ObjectNode proces = mapper.createObjectNode();
        proces.put("idProces", h.getIdProces());
        root.set("proces", proces);

        root.put("tipHotarare", h.getTipHotarare());
        root.put("dataPronuntare", h.getDataPronuntare());

        send("/hotarari", "POST", root);
    }

    public void deleteHotarare(Integer idHotarare) {
        send("/hotarari/" + idHotarare, "DELETE", null);
    }

    public List<SedintaToateDTO> getSedinteToate(
            String data,
            String nrDosar,
            boolean faraRezultat
    ) {
        try {
            String url = "/sedinte/toate";
            boolean first = true;

            if (data != null) {
                url += (first ? "?" : "&") + "data=" + data;
                first = false;
            }

            if (nrDosar != null) {
                url += (first ? "?" : "&") + "nrDosar=" + nrDosar;
                first = false;
            }

            if (faraRezultat) {
                url += (first ? "?" : "&") + "faraRezultat=true";
            }

            String json = send(url, "GET", null);

            return mapper.readValue(
                    json,
                    new TypeReference<List<SedintaToateDTO>>() {}
            );

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public Map<String, Integer> getNrSedintePerProces() {
        try {
            String json = send("/sedinte/nr-sedinte-per-proces", "GET", null);

            List<Map<String, Object>> list =
                    mapper.readValue(json, new TypeReference<>() {});

            Map<String, Integer> map = new HashMap<>();
            for (Map<String, Object> r : list) {
                map.put(
                        r.get("nrDosar").toString(),
                        Integer.parseInt(r.get("nrSedinte").toString())
                );
            }
            return map;

        } catch (Exception e) {
            e.printStackTrace();
            return Map.of();
        }
    }

    public List<Map<String, Object>> getProceseCuMinSedinte(int min) {
        try {
            String json = send(
                    "/sedinte/procese-cu-min-sedinte?min=" + min,
                    "GET",
                    null
            );

            return mapper.readValue(
                    json,
                    new TypeReference<List<Map<String, Object>>>() {}
            );

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<SedintaToateDTO> getSedinteFaraRezultat() {
        try {
            String json = send("/sedinte/fara-rezultat", "GET", null);
            return mapper.readValue(
                    json,
                    new TypeReference<List<SedintaToateDTO>>() {}
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<SedintaToateDTO> getSedinteImportante(String data) {
        try {
            String json = send(
                    "/sedinte/sedinte-importante?data=" + data,
                    "GET",
                    null
            );

            return mapper.readValue(
                    json,
                    new TypeReference<List<SedintaToateDTO>>() {}
            );
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    private <T> List<T> getList(String path, TypeReference<List<T>> ref) {
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + path))
                    .GET().build();

            String json = client.send(req, HttpResponse.BodyHandlers.ofString()).body();
            return mapper.readValue(json, ref);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    private String send(String path, String method, JsonNode body) {
        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + path))
                    .header("Content-Type", "application/json");

            if ("POST".equals(method) || "PUT".equals(method)) {
                builder.method(method,
                        HttpRequest.BodyPublishers.ofString(
                                body != null ? body.toString() : ""
                        ));
            } else {
                builder.method(method, HttpRequest.BodyPublishers.noBody());
            }

            HttpResponse<String> response =
                    client.send(builder.build(), HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

