PGDMP                  
    |            mercadofipp    15.7    16.3 .    '           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            (           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            )           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            *           1262    17167    mercadofipp    DATABASE     �   CREATE DATABASE mercadofipp WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Portuguese_Brazil.1252';
    DROP DATABASE mercadofipp;
                postgres    false                        2615    2200    public    SCHEMA     2   -- *not* creating schema, since initdb creates it
 2   -- *not* dropping schema, since initdb creates it
                postgres    false            +           0    0    SCHEMA public    ACL     Q   REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;
                   postgres    false    5            �            1259    17168    anuncio    TABLE     �   CREATE TABLE public.anuncio (
    anu_id integer NOT NULL,
    anu_title character varying(80),
    anu_date date,
    anu_desc text,
    anu_price numeric(10,1),
    cat_id integer,
    usr_id integer
);
    DROP TABLE public.anuncio;
       public         heap    postgres    false    5            �            1259    17173    anuncio_anu_id_seq    SEQUENCE     �   CREATE SEQUENCE public.anuncio_anu_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.anuncio_anu_id_seq;
       public          postgres    false    5    214            ,           0    0    anuncio_anu_id_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.anuncio_anu_id_seq OWNED BY public.anuncio.anu_id;
          public          postgres    false    215            �            1259    17174 	   categoria    TABLE     c   CREATE TABLE public.categoria (
    cat_id integer NOT NULL,
    cat_name character varying(20)
);
    DROP TABLE public.categoria;
       public         heap    postgres    false    5            �            1259    17177    categoria_cat_id_seq    SEQUENCE     �   CREATE SEQUENCE public.categoria_cat_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.categoria_cat_id_seq;
       public          postgres    false    216    5            -           0    0    categoria_cat_id_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.categoria_cat_id_seq OWNED BY public.categoria.cat_id;
          public          postgres    false    217            �            1259    17178    foto_anuncio    TABLE     {   CREATE TABLE public.foto_anuncio (
    fot_id integer NOT NULL,
    fot_file character varying(100),
    anu_id integer
);
     DROP TABLE public.foto_anuncio;
       public         heap    postgres    false    5            �            1259    17181    foto_anuncio_fot_id_seq    SEQUENCE     �   CREATE SEQUENCE public.foto_anuncio_fot_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public.foto_anuncio_fot_id_seq;
       public          postgres    false    5    218            .           0    0    foto_anuncio_fot_id_seq    SEQUENCE OWNED BY     S   ALTER SEQUENCE public.foto_anuncio_fot_id_seq OWNED BY public.foto_anuncio.fot_id;
          public          postgres    false    219            �            1259    17182    pergunta_anuncio    TABLE     �   CREATE TABLE public.pergunta_anuncio (
    per_id integer NOT NULL,
    per_text text,
    anu_id integer,
    per_resp text,
    usr_id integer
);
 $   DROP TABLE public.pergunta_anuncio;
       public         heap    postgres    false    5            �            1259    17187    pergunta_anuncio_per_id_seq    SEQUENCE     �   CREATE SEQUENCE public.pergunta_anuncio_per_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 2   DROP SEQUENCE public.pergunta_anuncio_per_id_seq;
       public          postgres    false    5    220            /           0    0    pergunta_anuncio_per_id_seq    SEQUENCE OWNED BY     [   ALTER SEQUENCE public.pergunta_anuncio_per_id_seq OWNED BY public.pergunta_anuncio.per_id;
          public          postgres    false    221            �            1259    17188    usuario    TABLE     �   CREATE TABLE public.usuario (
    usr_id integer NOT NULL,
    usr_name character varying(20),
    usr_pass character varying(10),
    usr_level character varying(1)
);
    DROP TABLE public.usuario;
       public         heap    postgres    false    5            �            1259    17191    usuario_usr_id_seq    SEQUENCE     �   CREATE SEQUENCE public.usuario_usr_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.usuario_usr_id_seq;
       public          postgres    false    222    5            0           0    0    usuario_usr_id_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.usuario_usr_id_seq OWNED BY public.usuario.usr_id;
          public          postgres    false    223            y           2604    17192    anuncio anu_id    DEFAULT     p   ALTER TABLE ONLY public.anuncio ALTER COLUMN anu_id SET DEFAULT nextval('public.anuncio_anu_id_seq'::regclass);
 =   ALTER TABLE public.anuncio ALTER COLUMN anu_id DROP DEFAULT;
       public          postgres    false    215    214            z           2604    17193    categoria cat_id    DEFAULT     t   ALTER TABLE ONLY public.categoria ALTER COLUMN cat_id SET DEFAULT nextval('public.categoria_cat_id_seq'::regclass);
 ?   ALTER TABLE public.categoria ALTER COLUMN cat_id DROP DEFAULT;
       public          postgres    false    217    216            {           2604    17194    foto_anuncio fot_id    DEFAULT     z   ALTER TABLE ONLY public.foto_anuncio ALTER COLUMN fot_id SET DEFAULT nextval('public.foto_anuncio_fot_id_seq'::regclass);
 B   ALTER TABLE public.foto_anuncio ALTER COLUMN fot_id DROP DEFAULT;
       public          postgres    false    219    218            |           2604    17195    pergunta_anuncio per_id    DEFAULT     �   ALTER TABLE ONLY public.pergunta_anuncio ALTER COLUMN per_id SET DEFAULT nextval('public.pergunta_anuncio_per_id_seq'::regclass);
 F   ALTER TABLE public.pergunta_anuncio ALTER COLUMN per_id DROP DEFAULT;
       public          postgres    false    221    220            }           2604    17196    usuario usr_id    DEFAULT     p   ALTER TABLE ONLY public.usuario ALTER COLUMN usr_id SET DEFAULT nextval('public.usuario_usr_id_seq'::regclass);
 =   ALTER TABLE public.usuario ALTER COLUMN usr_id DROP DEFAULT;
       public          postgres    false    223    222                      0    17168    anuncio 
   TABLE DATA           c   COPY public.anuncio (anu_id, anu_title, anu_date, anu_desc, anu_price, cat_id, usr_id) FROM stdin;
    public          postgres    false    214   4                 0    17174 	   categoria 
   TABLE DATA           5   COPY public.categoria (cat_id, cat_name) FROM stdin;
    public          postgres    false    216   �4                 0    17178    foto_anuncio 
   TABLE DATA           @   COPY public.foto_anuncio (fot_id, fot_file, anu_id) FROM stdin;
    public          postgres    false    218   5       !          0    17182    pergunta_anuncio 
   TABLE DATA           V   COPY public.pergunta_anuncio (per_id, per_text, anu_id, per_resp, usr_id) FROM stdin;
    public          postgres    false    220   �5       #          0    17188    usuario 
   TABLE DATA           H   COPY public.usuario (usr_id, usr_name, usr_pass, usr_level) FROM stdin;
    public          postgres    false    222   .6       1           0    0    anuncio_anu_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.anuncio_anu_id_seq', 6, true);
          public          postgres    false    215            2           0    0    categoria_cat_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.categoria_cat_id_seq', 5, true);
          public          postgres    false    217            3           0    0    foto_anuncio_fot_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.foto_anuncio_fot_id_seq', 5, true);
          public          postgres    false    219            4           0    0    pergunta_anuncio_per_id_seq    SEQUENCE SET     I   SELECT pg_catalog.setval('public.pergunta_anuncio_per_id_seq', 2, true);
          public          postgres    false    221            5           0    0    usuario_usr_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.usuario_usr_id_seq', 9, true);
          public          postgres    false    223                       2606    17198    anuncio anuncio_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.anuncio
    ADD CONSTRAINT anuncio_pkey PRIMARY KEY (anu_id);
 >   ALTER TABLE ONLY public.anuncio DROP CONSTRAINT anuncio_pkey;
       public            postgres    false    214            �           2606    17200    categoria categoria_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT categoria_pkey PRIMARY KEY (cat_id);
 B   ALTER TABLE ONLY public.categoria DROP CONSTRAINT categoria_pkey;
       public            postgres    false    216            �           2606    17202    foto_anuncio foto_anuncio_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.foto_anuncio
    ADD CONSTRAINT foto_anuncio_pkey PRIMARY KEY (fot_id);
 H   ALTER TABLE ONLY public.foto_anuncio DROP CONSTRAINT foto_anuncio_pkey;
       public            postgres    false    218            �           2606    17204 &   pergunta_anuncio pergunta_anuncio_pkey 
   CONSTRAINT     h   ALTER TABLE ONLY public.pergunta_anuncio
    ADD CONSTRAINT pergunta_anuncio_pkey PRIMARY KEY (per_id);
 P   ALTER TABLE ONLY public.pergunta_anuncio DROP CONSTRAINT pergunta_anuncio_pkey;
       public            postgres    false    220            �           2606    17206    usuario usuario_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (usr_id);
 >   ALTER TABLE ONLY public.usuario DROP CONSTRAINT usuario_pkey;
       public            postgres    false    222            �           2606    17207    anuncio anuncio_cat_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.anuncio
    ADD CONSTRAINT anuncio_cat_id_fkey FOREIGN KEY (cat_id) REFERENCES public.categoria(cat_id) NOT VALID;
 E   ALTER TABLE ONLY public.anuncio DROP CONSTRAINT anuncio_cat_id_fkey;
       public          postgres    false    216    3201    214            �           2606    17212    anuncio anuncio_usr_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.anuncio
    ADD CONSTRAINT anuncio_usr_id_fkey FOREIGN KEY (usr_id) REFERENCES public.usuario(usr_id) NOT VALID;
 E   ALTER TABLE ONLY public.anuncio DROP CONSTRAINT anuncio_usr_id_fkey;
       public          postgres    false    214    3207    222            �           2606    17217 %   foto_anuncio foto_anuncio_anu_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.foto_anuncio
    ADD CONSTRAINT foto_anuncio_anu_id_fkey FOREIGN KEY (anu_id) REFERENCES public.anuncio(anu_id);
 O   ALTER TABLE ONLY public.foto_anuncio DROP CONSTRAINT foto_anuncio_anu_id_fkey;
       public          postgres    false    214    218    3199            �           2606    17222 -   pergunta_anuncio pergunta_anuncio_anu_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.pergunta_anuncio
    ADD CONSTRAINT pergunta_anuncio_anu_id_fkey FOREIGN KEY (anu_id) REFERENCES public.anuncio(anu_id);
 W   ALTER TABLE ONLY public.pergunta_anuncio DROP CONSTRAINT pergunta_anuncio_anu_id_fkey;
       public          postgres    false    3199    220    214            �           2606    17227 -   pergunta_anuncio pergunta_usuario_usr_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.pergunta_anuncio
    ADD CONSTRAINT pergunta_usuario_usr_id_fkey FOREIGN KEY (usr_id) REFERENCES public.usuario(usr_id) NOT VALID;
 W   ALTER TABLE ONLY public.pergunta_anuncio DROP CONSTRAINT pergunta_usuario_usr_id_fkey;
       public          postgres    false    3207    220    222               �   x�u�1�0 g�~ ��E�X1"�J�t���:�AIxO�c���=Xw>�}X��+{�؃Ѧ.H���[l����-�#��S��h}F���ȳ�Rf6�9N�}�����R�QH��:Ԯ���ZUA��K���n�@D_`4�;�߯�[ N�7�R)�i<�         C   x�3���K�/�=��$39�ˈ3'��(��˄319��8�(�1�LN�)�I,J-�2�442����� ~��         y   x�3�t����M-JNL�w�,(��)-��OL)��1476203�07060�N�U(9���4'_� /�3Əˈ�fC#sS3Sl��	kbl�Mk661022¦ٔ�fsKss�f\1z\\\ |vf      !   v   x�3�tK�RHI-N��+�W8�@�,��$ўӐ���f��T���"��|� ��?.#Π��Ԣ�D�ԜD��ĢD���\=�r��̒|E�ļ�D�z��������������%Ȉ=... K�#�      #   M   x�3�LL�aC#cNC.#ά�|q���9�S����D�	�WF~9���)��X�F5�"j�e�	���=... � &      