#!/usr/bin/env python

import os
import sys
import glob
import pathlib
import functools

DEBUG = False

################################################################### PRELUDE

def dbg(*a, **k):
    if DEBUG:
        print('[debug]', end=' ')
        print(*a)

def zipmap(a,b,f):
    return [f(i,j) for i,j in zip(a,b)]

def vmatch(a, b, var=None):
    return all(zipmap(a,b,lambda u,v: u is var or v is var or u == v))

################################################################### CORE

def s(root, pred):
    return ((top,fn)
            for top,dirs,files in os.walk(root)
            for fn in files
            if pred(top, fn))

def sourceset(cli, ext='.java'):
    ''' flag -> path -> str<os> '''
    flag, root, *_ = cli
    root = pathlib.Path(root)
    dbg('sourceset', flag, root, _)
    fn_format = lambda t,f: t + os.path.sep + f
    set_format = lambda a,b: a + ' ' + b
    files = s(root, lambda t,f: f.endswith(ext))
    return functools.reduce(set_format, (fn_format(t,f) for t,f in files))
    
def libs(cli, ext='.jar'):
    ''' flag -> path -> str<os> '''
    flag, root, *_ = cli
    root = pathlib.Path(root)
    sep = os.path.pathsep
    dbg('libs', flag, root, _)
    dbg(sep)
    fn_format = lambda t,f: t + os.path.sep + f
    set_format = lambda a,b: a + os.path.pathsep + b
    files = s(root, lambda t,f: f.endswith(ext))
    return functools.reduce(set_format, ((t + os.path.sep + f) for t,f in files))

def usage():
    print(__file__, ':')
    print()
    print(__file__, '--source', '<dir>')
    print(__file__, '--libs', '<dir>')

ROOT=pathlib.Path('src') / 'jnpn' / 'json'
def main():
    dbg(os.getcwd())
    dbg(sys.platform)
    this, *cli = sys.argv
    if cli:
        if vmatch(cli, ['--sources', None]):
            print(sourceset(cli))
        elif vmatch(cli, ['--libs', None]):
            print(libs(cli))
        else:
            print('wat')
    else:
        usage()

if __name__ == '__main__':
    main()
