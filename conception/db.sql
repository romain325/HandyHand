--
-- PostgreSQL database dump
--

-- Dumped from database version 12.5 (Ubuntu 12.5-0ubuntu0.20.04.1)
-- Dumped by pg_dump version 12.5 (Ubuntu 12.5-0ubuntu0.20.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: exec; Type: TABLE; Schema: public; Owner: romain
--

CREATE TABLE public.exec (
    exectype character varying NOT NULL,
    path character varying NOT NULL
);


ALTER TABLE public.exec OWNER TO romain;

--
-- Name: script; Type: TABLE; Schema: public; Owner: romain
--

CREATE TABLE public.script (
    idscript integer NOT NULL,
    args character varying,
    content character varying NOT NULL,
    exectype character varying,
    isprivate boolean
);


ALTER TABLE public.script OWNER TO romain;

--
-- Name: script_idscript_seq; Type: SEQUENCE; Schema: public; Owner: romain
--

ALTER TABLE public.script ALTER COLUMN idscript ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.script_idscript_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: user; Type: TABLE; Schema: public; Owner: romain
--

CREATE TABLE public."user" (
    userid integer NOT NULL,
    mail character varying NOT NULL,
    password character varying NOT NULL
);


ALTER TABLE public."user" OWNER TO romain;

--
-- Name: user_userid_seq; Type: SEQUENCE; Schema: public; Owner: romain
--

ALTER TABLE public."user" ALTER COLUMN userid ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.user_userid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Data for Name: exec; Type: TABLE DATA; Schema: public; Owner: romain
--

COPY public.exec (exectype, path) FROM stdin;
\.


--
-- Data for Name: script; Type: TABLE DATA; Schema: public; Owner: romain
--

COPY public.script (idscript, args, content, exectype, isprivate) FROM stdin;
\.


--
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: romain
--

COPY public."user" (userid, mail, password) FROM stdin;
\.


--
-- Name: script_idscript_seq; Type: SEQUENCE SET; Schema: public; Owner: romain
--

SELECT pg_catalog.setval('public.script_idscript_seq', 2, true);


--
-- Name: user_userid_seq; Type: SEQUENCE SET; Schema: public; Owner: romain
--

SELECT pg_catalog.setval('public.user_userid_seq', 1, false);


--
-- Name: exec exec_pk; Type: CONSTRAINT; Schema: public; Owner: romain
--

ALTER TABLE ONLY public.exec
    ADD CONSTRAINT exec_pk PRIMARY KEY (exectype);


--
-- Name: script script_pk; Type: CONSTRAINT; Schema: public; Owner: romain
--

ALTER TABLE ONLY public.script
    ADD CONSTRAINT script_pk PRIMARY KEY (idscript);


--
-- Name: user user_pk; Type: CONSTRAINT; Schema: public; Owner: romain
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pk PRIMARY KEY (userid);


--
-- Name: script script_fk; Type: FK CONSTRAINT; Schema: public; Owner: romain
--

ALTER TABLE ONLY public.script
    ADD CONSTRAINT script_fk FOREIGN KEY (exectype) REFERENCES public.exec(exectype);


--
-- PostgreSQL database dump complete
--

